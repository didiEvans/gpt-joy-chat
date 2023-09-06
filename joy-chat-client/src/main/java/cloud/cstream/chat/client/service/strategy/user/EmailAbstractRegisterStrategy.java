package cloud.cstream.chat.client.service.strategy.user;

import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.domain.vo.LoginInfoVO;
import cloud.cstream.chat.common.constants.ApplicationConstant;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.enums.EmailBizTypeEnum;
import cloud.cstream.chat.common.enums.InviteTaskStageEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.EncryptUtil;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.ClientUserVerifyInfoDO;
import cloud.cstream.chat.core.domain.entity.EmailVerifyCodeDO;
import cloud.cstream.chat.core.service.*;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static cloud.cstream.chat.common.constants.ApplicationConstant.FRONT_JWT_EXTRA_USER_ID;
import static cloud.cstream.chat.common.constants.ApplicationConstant.FRONT_JWT_USERNAME;


/**
 * 邮箱注册策略
 *
 * @author CoDeleven
 */
@Lazy
@Service
public class EmailAbstractRegisterStrategy extends AbstractRegisterTypeStrategy {

    @Resource
    private ClientUserVerifyInfoService userVerifyInfoService;
    @Resource
    private ClientUserInfoService userInfoService;
    @Resource
    private EmailVerifyCodeService emailVerifyCodeService;
    @Resource
    private SysFrontUserLoginLogService loginLogService;
    @Resource
    private EmailService emailService;
    @Resource
    private FrontUserInviteTaskRecordService taskRecordService;


    @Override
    public boolean identityUsed(String identity) {
        return userVerifyInfoService.isUsed(identity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkVerifyCode(String identity, String verifyCode, String encryptInviteCode) {
        // 校验邮箱验证码
        EmailVerifyCodeDO availableVerifyCode = emailVerifyCodeService.findAvailableByVerifyCode(verifyCode);
        if (Objects.isNull(availableVerifyCode)) {
            throw new ServiceException("验证码不存在或已过期，请重新发起...");
        }
        // 验证通过，生成基础用户信息并做绑定
        ClientUserInfoDO userInfo = userInfoService.findUserInfoById(availableVerifyCode.getUid());
        // 构建邀请任务记录
        taskRecordService.initInviteRecords(userInfo.getInviteUid(), userInfo.getId());
        // 处理邀请人用户注册任务
        taskRecordService.tryCompleteTaskStage(InviteTaskStageEnum.REGISTRATION_SUCCESSFUL, userInfo.getInviteUid(), userInfo.getId());
        // 获取邮箱信息表
        ClientUserVerifyInfoDO verifyInfoDO = userVerifyInfoService.getUserVerifiedInfo(userInfo.getId());
        // 验证完毕，写入日志
        emailVerifyCodeService.verifySuccess(availableVerifyCode);
        // 设置邮箱已验证
        userVerifyInfoService.verifySuccess(verifyInfoDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterFrontUserRequest request) {
        // 是否已存在该邮箱注册用户
        ClientUserInfoDO userInfoDO = userInfoService.getClientUserInfoByEmailAccount(request.getIdentity());
        String salt = RandomUtil.randomString(6);
        // 构建新的邮箱信息
        if (Objects.isNull(userInfoDO)) {
            // 为空, 构建新用户信息
            userInfoDO = userInfoService.initializeClientUser(request.getInviteCode(), ClientUserRegisterTypeEnum.EMAIL, request.getIdentity() , this.encryptRawPassword(request.getPassword(), salt));
            // 构建邮箱验证信息
            ClientUserVerifyInfoDO verifyInfo = ClientUserVerifyInfoDO.builder()
                    .uid(userInfoDO.getId())
                    .salt(salt)
                    .verified(false).build();
            // 存储邮箱验证信息
            userVerifyInfoService.save(verifyInfo);
        } else {
            // 查询验证信息
            ClientUserVerifyInfoDO verifyInfoDO = userVerifyInfoService.queryUserVerifyInfo(userInfoDO.getId());
            // 如果已验证, 则邮箱不允许使用
            if (verifyInfoDO.getVerified()) {
                throw new ServiceException("邮箱已注册");
            }
            // 在未使用的邮箱基础上更新下密码信息，然后重新投入使用
            verifyInfoDO.setSalt(salt);
            verifyInfoDO.setVerified(false);
            // 更新验证记录
            userVerifyInfoService.updateById(verifyInfoDO);
            // 更新密码
            userInfoDO.setPassword(EncryptUtil.md5Encrypt(request.getPassword(), salt));
            userInfoService.updateById(userInfoDO);
        }
        // 存储验证码记录
        EmailVerifyCodeDO emailVerifyCodeDO = emailVerifyCodeService.createVerifyCode(EmailBizTypeEnum.REGISTER_VERIFY, request.getIdentity(), userInfoDO.getId());
        // 发送邮箱验证信息
        emailService.sendForVerifyCode(request.getIdentity(), request.getInviteCode(), emailVerifyCodeDO.getVerifyCode());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginInfoVO login(String username, String password, Integer loginType, String captcha, String inviteCode) {
        // 验证账号信息
        ClientUserInfoDO userInfo = userInfoService.getClientUserInfoByEmailAccount(username);
        if (Objects.isNull(userInfo)) {
            throw new ServiceException("邮箱未注册");
        }
        if (BoolEnum.isTrue(userInfo.getStatus())){
            throw new ServiceException("账号违规,已被禁用！");
        }
        ClientUserVerifyInfoDO verifyInfoDO = userVerifyInfoService.getUserVerifiedInfo(userInfo.getId());
        if (Boolean.FALSE.equals(verifyInfoDO.getVerified())){
            throw new ServiceException("邮箱未验证，请重新注册");
        }
        // 二次加密，验证账号密码
        String salt = verifyInfoDO.getSalt();
        String afterEncryptedPassword = EncryptUtil.md5Encrypt(password, salt);
        if (!Objects.equals(afterEncryptedPassword, userInfo.getPassword())) {
            // 获取绑定的基础用户 id
            loginLogService.loginFailed(ClientUserRegisterTypeEnum.EMAIL, userInfo.getId(), "账号或密码错误");
            throw new ServiceException("账号或密码错误");
        }
        // 获取登录用户信息
        StpUtil.login(userInfo.getId(), SaLoginModel.create()
                .setExtra(FRONT_JWT_USERNAME, userInfo.getEmailAddress())
                .setExtra(ApplicationConstant.FRONT_JWT_REGISTER_TYPE_CODE, ClientUserRegisterTypeEnum.EMAIL.getCode())
                .setExtra(FRONT_JWT_EXTRA_USER_ID, verifyInfoDO.getId()));

        // 记录登录日志
        loginLogService.loginSuccess(ClientUserRegisterTypeEnum.EMAIL, verifyInfoDO.getId(), userInfo.getId());

        return LoginInfoVO.builder().token(StpUtil.getTokenValue()).baseUserId(userInfo.getId()).build();
    }
}
package cloud.cstream.chat.client.service.strategy.user;

import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.domain.vo.LoginInfoVO;
import cloud.cstream.chat.common.constants.ApplicationConstant;
import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.common.constants.RedisKeyConstant;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.enums.InviteTaskStageEnum;
import cloud.cstream.chat.common.enums.LoginTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.EncryptUtil;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.ClientUserVerifyInfoDO;
import cloud.cstream.chat.core.service.*;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static cloud.cstream.chat.common.constants.ApplicationConstant.FRONT_JWT_EXTRA_USER_ID;
import static cloud.cstream.chat.common.constants.ApplicationConstant.FRONT_JWT_USERNAME;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Lazy
@Service
public class PhoneAbstractRegisterStrategy extends AbstractRegisterTypeStrategy {

    @Autowired
    private ClientUserVerifyInfoService userVerifyInfoService;
    @Autowired
    private ClientUserWalletService walletService;
    @Autowired
    private FrontUserInviteTaskRecordService taskRecordService;
    @Autowired
    private SysFrontUserLoginLogService loginLogService;
    @Autowired
    private ClientUserInfoService userInfoService;
    @Autowired
    private RedisClient redisClient;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public boolean identityUsed(String identity) {
        return userVerifyInfoService.isUsed(identity);
    }

    @Override
    public void register(RegisterFrontUserRequest request) {
        ClientUserInfoDO userInfo = userInfoService.getClientUserInfoByMobilePhone(request.getIdentity());
        String salt = RandomUtil.randomString(6);
        // 构建新的号码信息
        if (Objects.isNull(userInfo)) {
            // 构建用户
            userInfo = userInfoService.initializeClientUser(request.getInviteCode(), ClientUserRegisterTypeEnum.PHONE, request.getIdentity(), EncryptUtil.md5Encrypt(request.getPassword(), salt));
            // 前面已经校验过验证吗
            ClientUserVerifyInfoDO verifyInfoDO = ClientUserVerifyInfoDO.builder()
                    .bizType(ClientUserRegisterTypeEnum.PHONE)
                    .uid(userInfo.getId())
                    .salt(salt)
                    .verified(true).build();
            // 存储号码验证信息
            userVerifyInfoService.save(verifyInfoDO);
        } else {
            // 查询验证结果, 防止用户重复注册或机器人批量注册幂等
            ClientUserVerifyInfoDO verifyInfoDO = userVerifyInfoService.getUserVerifiedInfo(userInfo.getId());
            if (verifyInfoDO.getVerified()){
                throw new ServiceException("手机号已注册");
            }
            verifyInfoDO.setSalt(salt);
            verifyInfoDO.setVerified(true);
            // 更新电话信息
            userVerifyInfoService.updateById(verifyInfoDO);
            // 更新用户密码
            userInfo.setPassword(EncryptUtil.md5Encrypt(request.getPassword(), salt));
            userInfoService.updateById(userInfo);
        }
        // 初始化钱包
        walletService.initUserWallet(userInfo.getId());
        // 构建邀请任务记录
        taskRecordService.initInviteRecords(userInfo.getInviteUid(), userInfo.getId());
        // 处理邀请人用户注册任务
        taskRecordService.tryCompleteTaskStage(InviteTaskStageEnum.REGISTRATION_SUCCESSFUL, userInfo.getInviteUid(), userInfo.getId());
    }

    @Override
    public void checkVerifyCode(String identity, String verifyCode, String encryptInviteCode) {
        if (CoreConstants.TEST_CAPTCHA.equals(verifyCode) && active.equals(CoreConstants.DEV_ENV)){
            return;
        }
        String redisKey = String.format(RedisKeyConstant.USER_LOGIN_BY_PHONE_VERIFY_CODE, identity);
        String codeCache = redisClient.get(redisKey);
        if (CharSequenceUtil.isBlankOrUndefined(codeCache)) {
            throw new ServiceException("验证码已失效");
        }
        if (!codeCache.equals(verifyCode)) {
            // 如果验证码不正确, 则删除该缓存, 防止暴力破解
            redisClient.delete(redisKey);
            throw new ServiceException("验证码错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginInfoVO login(String username, String password, Integer loginType, String captcha, String inviteCode) {
        // 查询号码是否已被注册
        boolean used = this.identityUsed(username);
        if (!used) {
            // 注册
            this.registryPhoneUser(username, password, inviteCode, captcha);
            // 未注册时,默认密码登录
            loginType = LoginTypeEnum.PWD.getType();
        }
        // 登录
        return doLogin(username, password, loginType, captcha);
    }

    private void registryPhoneUser(String username, String password, String inviteCode, String captcha) {
        // 校验验证码
        this.checkVerifyCode(username, captcha, null);
        // 验证码通过
        this.register(RegisterFrontUserRequest.builder()
                .inviteCode(inviteCode)
                .identity(username)
                .password(password)
                .registerType(ClientUserRegisterTypeEnum.PHONE).build());
    }

    private LoginInfoVO doLogin(String username, String password, Integer loginType, String captcha) {
        // 登录
        ClientUserInfoDO userInfo = userInfoService.getClientUserInfoByMobilePhone(username);
        if (Objects.isNull(userInfo)){
            throw new ServiceException("请注册后再行登录");
        }
        if (BoolEnum.isTrue(userInfo.getStatus())){
            throw new ServiceException("账号存在违规行为,已被禁用");
        }
         ClientUserVerifyInfoDO verifyInfoDO = userVerifyInfoService.getUserVerifiedInfo(userInfo.getId());
        if (!verifyInfoDO.getVerified()){
            throw new ServiceException("请注册后再行登录");
        }
        switch (LoginTypeEnum.load(loginType)) {
            case PWD -> {
                return passwordLogin(password, userInfo, verifyInfoDO.getSalt());
            }
            case CODE -> {
                return authCodeLogin(captcha, userInfo);
            }
            default -> {
                throw new ServiceException("登录异常:未知的登录方式");
            }
        }
    }

    private LoginInfoVO authCodeLogin(String captcha, ClientUserInfoDO userInfo) {
        try {
            this.checkVerifyCode(userInfo.getMobilePhone(), captcha, null);
        } catch (ServiceException bizExc) {
            // 验证码验证失败
            this.afterLoginFail(userInfo.getId());
            throw bizExc;
        }
        // 验证成功
        return loginSuccess(userInfo);
    }

    private LoginInfoVO passwordLogin(String requestPassword, ClientUserInfoDO userInfo, String salt) {
        // 二次加密，验证账号密码
        String afterEncryptedPassword = this.encryptRawPassword(requestPassword, salt);
        if (!Objects.equals(afterEncryptedPassword, userInfo.getPassword())) {
            this.afterLoginFail(userInfo.getId());
        }
        return loginSuccess(userInfo);
    }

    private LoginInfoVO loginSuccess(ClientUserInfoDO userInfo) {
        // 执行登录
        StpUtil.login(userInfo.getId(), SaLoginModel.create()
                .setExtra(FRONT_JWT_USERNAME, userInfo.getMobilePhone())
                .setExtra(ApplicationConstant.FRONT_JWT_REGISTER_TYPE_CODE, ClientUserRegisterTypeEnum.EMAIL.getCode())
                .setExtra(FRONT_JWT_EXTRA_USER_ID, userInfo.getId()));
        // 记录登录日志
        loginLogService.loginSuccess(ClientUserRegisterTypeEnum.EMAIL, userInfo.getId(), userInfo.getId());
        // 执行登录
        return LoginInfoVO.builder().token(StpUtil.getTokenValue()).baseUserId(userInfo.getId()).build();
    }

    private void afterLoginFail(Integer userId) {
        // 记录登录失败日志
        loginLogService.loginFailed(ClientUserRegisterTypeEnum.EMAIL, userId, "账号或密码错误");
        throw new ServiceException("账号或密码错误");
    }

}

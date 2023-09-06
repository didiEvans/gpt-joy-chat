package cloud.cstream.chat.client.service.impl;

import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.domain.request.SendSmsCaptchaRequest;
import cloud.cstream.chat.client.domain.vo.LoginInfoVO;
import cloud.cstream.chat.client.domain.vo.RegisterCaptchaVO;
import cloud.cstream.chat.client.domain.vo.UserInfoVO;
import cloud.cstream.chat.client.service.ClientUserService;
import cloud.cstream.chat.client.service.strategy.user.AbstractRegisterTypeStrategy;
import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.constants.RedisKeyConstant;
import cloud.cstream.chat.common.constants.StrConstants;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.enums.SmsSendHandlerEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.EncryptUtil;
import cloud.cstream.chat.common.util.MatchUtil;
import cloud.cstream.chat.common.util.SimpleCaptchaUtil;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.ClientUserVerifyInfoDO;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cloud.cstream.chat.core.domain.query.InviteePageQuery;
import cloud.cstream.chat.core.domain.request.UserProfileInfoRequest;
import cloud.cstream.chat.core.domain.vo.InviterInfoVO;
import cloud.cstream.chat.core.domain.vo.UserInviteeListVO;
import cloud.cstream.chat.core.handler.SensitiveWordHandler;
import cloud.cstream.chat.core.service.*;
import cloud.cstream.chat.core.strategy.sms.AbstractSendSmsTypeStrategy;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.*;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * tips
 *
 * @author YBin
 * @date 2023/4/25 16:52
 */
@Service
public class ClientUserServiceImpl implements ClientUserService {

    @Autowired
    private FrontUserInviteTaskRecordService userInviteTaskRecordService;
    @Autowired
    private ClientUserWalletService walletService;
    @Autowired
    private ClientUserInfoService userInfoService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private FileService fileService;
    @Autowired
    private ClientUserVerifyInfoService verifyInfoService;



    @Override
    public void register(RegisterFrontUserRequest request) {
        AbstractRegisterTypeStrategy registerStrategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(request.getRegisterType());
        registerStrategy.register(request);
    }

    @Override
    public void verifyCode(ClientUserRegisterTypeEnum registerType, String code, String encryptInviteCode) {
        AbstractRegisterTypeStrategy registerStrategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(registerType);
        registerStrategy.checkVerifyCode(null, code, encryptInviteCode);
    }

    @Override
    public LoginInfoVO login(ClientUserRegisterTypeEnum registerType, String username, String password, Integer loginType, String captcha, String inviteCode) {
        AbstractRegisterTypeStrategy strategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(registerType);
        return strategy.login(username, password, loginType, captcha, inviteCode);
    }

    @Override
    public UserInfoVO getUserInfo(Integer uid) {
        // 根据绑定关系查找基础用户信息
        ClientUserInfoDO userInfo = userInfoService.findUserInfoById(uid);
        if (Objects.isNull(userInfo)) {
            throw new ServiceException(StrUtil.format("非法请求,用户不存在"));
        }
        // 封装基础用户信息并返回
        return UserInfoVO.builder().baseUserId(userInfo.getId())
                .totalSessionCount(userInfo.getChatCount())
                .description(userInfo.getDescription())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmailAddress())
                .mobilePhone(DesensitizedUtil.mobilePhone(userInfo.getMobilePhone()))
                .residualSessionCount(this.calculateResidualSessionCount(userInfo))
                .inviterUserCount(userInfoService.getCurrentUserInviteUserCount(uid))
                .points(walletService.getUserWallet(uid).getPoints())
                .avatarVersion(fileService.getPic(userInfo.getAvatarVersion())).build();
    }

    @Override
    public RegisterCaptchaVO generateCaptcha() {
        // 创建一个 图形验证码会话ID
        String picCodeSessionId = IdUtil.fastUUID();
        // 根据图形验证码会话ID获取一个图形验证码。该方法会建立起 验证码会话ID 和 图形验证码的关系
        String captchaBase64Image = SimpleCaptchaUtil.getBase64Captcha(picCodeSessionId);
        // 将验证码会话ID加入到Cookie中
        return RegisterCaptchaVO.builder().picCodeSessionId(picCodeSessionId).picCodeBase64(captchaBase64Image).build();
    }

    @Override
    public UserInfoVO getLoginUserInfo() {
        return this.getUserInfo(ClientUserLoginUtil.getUserId());
    }

    @Override
    public InviterInfoVO getInviterInfo(Integer userId) {
        return userInfoService.getInviterInfo(userId);
    }


    @Override
    public String getRechargeQrCode() {
        /*
         * 随机访问List元素
         */
        List<String> rechargeQrCode = userInfoService.getRechargeQrCode();
        int idx = RandomUtil.randomInt(rechargeQrCode.size());
        return rechargeQrCode.get(idx);
    }

    @Override
    public void sendCaptcha(SendSmsCaptchaRequest request) {
        Assert.isTrue(checkInvokeLimit(WebUtil.getIp()), () -> new ServiceException("疑似危险调用,若证实,将被永久封禁!!"));
        Assert.isTrue(PhoneUtil.isMobile(request.getPhone()), () -> new ServiceException("手机号码格式有误"));
        Assert.isTrue(SimpleCaptchaUtil.verifyCaptcha(request.getPicCodeSessionId(), request.getPicVerificationCode()), () -> new ServiceException("图形验证码有误"));
        Assert.isTrue(checkInvokeLimit(request.getPhone()), () -> new ServiceException("今日验证码发发送次数已达上限"));
        // 执行发送验证码
        String captcha = RandomUtil.randomString(StrConstants.BASE_INT, 4);
        boolean b = AbstractSendSmsTypeStrategy.findStrategyHandler(SmsSendHandlerEnum.TENCENT).sendCaptcha(request.getPhone(), captcha);
        if (!b) {
            throw new ServiceException("短信发送失败");
        }
        String key = String.format(RedisKeyConstant.USER_LOGIN_BY_PHONE_VERIFY_CODE, request.getPhone());
        redisClient.setEx(key, captcha, 5, TimeUnit.MINUTES);
    }


    @Override
    public Page<UserInviteeListVO> getUserInviteeList(InviteePageQuery pageQuery, Integer userId) {
        if (Objects.nonNull(pageQuery)){
            pageQuery = new InviteePageQuery();
        }
        pageQuery.setInviterUId(userId);
        return userInviteTaskRecordService.queryInviteeList(pageQuery);
    }


    @Override
    public void editUserProfileInfo(UserProfileInfoRequest request) {
        Assert.isFalse(MatchUtil.containSpecialChar(request.getNickname()), () -> new ServiceException("昵称含有特殊字符"));
        List<String> hitEl = SensitiveWordHandler.checkWord(request.getNickname());
        Assert.isTrue(CollUtil.isEmpty(hitEl), () -> new ServiceException("昵称内含有敏感词,请修改后再行提交"));
        userInfoService.update(Wrappers.<ClientUserInfoDO>lambdaUpdate().set(ClientUserInfoDO::getNickname, request.getNickname()).eq(ClientUserInfoDO::getId, ClientUserLoginUtil.getUserId()));
    }


    @Override
    public void resetUserPwd(UserProfileInfoRequest request) {
        ClientUserInfoDO userInfo = userInfoService.findUserInfoById(ClientUserLoginUtil.getUserId());
        ClientUserVerifyInfoDO verifyInfoDO = verifyInfoService.getUserVerifiedInfo(ClientUserLoginUtil.getUserId());
        if (Objects.isNull(verifyInfoDO)){
            throw new ServiceException("账号未验证");
        }
        String md5OriginalPwd = EncryptUtil.md5Encrypt(request.getOriginalPwd(), verifyInfoDO.getSalt());
        if (!userInfo.getPassword().equals(md5OriginalPwd)) {
            throw new ServiceException("原密码错误");
        }
        if (!request.getNewPwd().equals(request.getRepeatNewPwd())) {
            throw new ServiceException("两次输入密码不一致");
        }
        String md5NewPwd = EncryptUtil.md5Encrypt(request.getOriginalPwd(), verifyInfoDO.getSalt());
        userInfo.setPassword(md5NewPwd);
        userInfoService.updateById(userInfo);
    }

    private boolean checkInvokeLimit(String checkFlag) {
        Integer count;
        String limitKey;
        limitKey = String.format(RedisKeyConstant.USER_SEND_CAPTCHA_LIMIT, DatePattern.PURE_DATE_FORMAT.format(DateUtil.date()), checkFlag);
        count = redisClient.getInt(limitKey);
        if (!Objects.isNull(count)) {
            if (count.compareTo(CoreConstants.LIMIT_SEND_CAPTCHA_COUNT) > 0) {
                return false;
            }
            redisClient.incrBy(limitKey, NumConstant.INT_ONE);
        } else {
            redisClient.setEx(limitKey, NumConstant.INT_ONE, 1, TimeUnit.DAYS);
        }
        return true;
    }


    protected Integer calculateResidualSessionCount(ClientUserInfoDO baseUser) {
        String cacheKey = String.format(RedisKeyConstant.USER_FREE_SESSION_COUNT_CACHE, DatePattern.PURE_DATE_FORMAT.format(DateUtil.date()), baseUser.getId());
        Integer sessionCount = redisClient.getInt(cacheKey);
        if (Objects.isNull(sessionCount)) {
            sessionCount = NumConstant.INT_ZERO;
        }
        return baseUser.getChatCount() - sessionCount;
    }

    @Override
    public Boolean phoneExists(String phone) {
        return Objects.nonNull(userInfoService.getClientUserInfoByMobilePhone(phone));
    }

    @Override
    public List<CosFileResourcePair> getUserExtraResources(Integer userId) {
        ClientUserInfoDO userInfo = userInfoService.findUserInfoById(userId);
        List<CosFileResourcePair> pairs = Lists.newArrayList();
        if (Objects.isNull(userInfo)){
            return pairs;
        }
        // 获取用户头像地址
        CosFileResourcePair avatarResource = CosFileResourcePair.builder().field("avatarVersion").fileKeys(Lists.newArrayList(userInfo.getAvatarVersion())).build();
        // 如果首页还有其它与用户相关的图片资源可以放在这里
        pairs.add(avatarResource);
        return fileService.batchGetResource(pairs);
    }

    @Override
    public String resetUserAvatar(MultipartFile file, Integer userId) {
        ClientUserInfoDO userInfoById = userInfoService.findUserInfoById(userId);
        if (Objects.isNull(userInfoById)){
            throw new ServiceException("用户不存在");
        }
        String fileKey = fileService.uploadClientAvatar(file, userId);
        userInfoById.setAvatarVersion(fileKey);
        userInfoService.updateById(userInfoById);
        return fileService.getPic(fileKey);
    }

    @Override
    public void uploadClientAvatar(MultipartFile file, Integer userId) {
        ClientUserInfoDO user = userInfoService.findUserInfoById(userId);
        if (Objects.isNull(user)){
            return;
        }
        // 执行上传
        String filePath = fileService.uploadClientAvatar(file, userId);
        user.setAvatarVersion(filePath);
        userInfoService.updateById(user);
    }
}

package cloud.cstream.chat.client.service;


import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.domain.request.SendSmsCaptchaRequest;
import cloud.cstream.chat.client.domain.vo.LoginInfoVO;
import cloud.cstream.chat.client.domain.vo.RegisterCaptchaVO;
import cloud.cstream.chat.client.domain.vo.UserInfoVO;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cloud.cstream.chat.core.domain.query.InviteePageQuery;
import cloud.cstream.chat.core.domain.request.UserProfileInfoRequest;
import cloud.cstream.chat.core.domain.vo.InviterInfoVO;
import cloud.cstream.chat.core.domain.vo.UserInviteeListVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientUserService {

    /**
     * 处理注册请求
     *
     * @param request 注册请求
     */
    void register(RegisterFrontUserRequest request);

    /**
     * 验证码验证
     *
     * @param frontUserRegisterTypeEnum 注册类型
     * @param code                      验证码，可以是邮箱也可以是手机
     * @param encryptInviteCode         加密的邀请码
     */
    void verifyCode(ClientUserRegisterTypeEnum frontUserRegisterTypeEnum, String code, String encryptInviteCode);

    /**
     * 执行登录
     *
     * @param registerType 注册类型
     * @param username     登录用户名，可以是邮箱，可以是手机
     * @param password     登录口令
     * @param loginType    登录方式, 1 密码登录, 2 验证码登录
     * @param captcha      验证码
     * @param inviteCode   邀请码
     * @return Sa-Token的登录结果
     */
    LoginInfoVO login(ClientUserRegisterTypeEnum registerType, String username, String password, Integer loginType, String captcha, String inviteCode);

    /**
     * 根据注册类型+其他绑定信息表获取该用户的登录信息
     *
     * @param userId  用户id
     * @return 登录用户信息
     */
    UserInfoVO getUserInfo(Integer userId);

    /**
     * 根据当前登录的状态获取用户信息
     *
     * @return 登录的用户信息
     */
    UserInfoVO getLoginUserInfo();

    /**
     * 生成基于 Base64 的图形验证码
     *
     * @return Base64 图形验证码
     */
    RegisterCaptchaVO generateCaptcha();

    /**
     * 获取邀请人邀请信息
     *
     * @param userId 前端用户id
     * @return
     */
    InviterInfoVO getInviterInfo(Integer userId);

    /**
     * 获取充值二维码信息
     *
     * @return {@link List<String>} 二维码链接
     */
    String getRechargeQrCode();

    /**
     * 发送短信验证码
     *
     * @param request 电话好啊没
     */
    void sendCaptcha(SendSmsCaptchaRequest request);

    /**
     * 获取用户邀请人列表
     *
     * @param pageQuery
     * @param userId
     * @return
     */
    Page<UserInviteeListVO> getUserInviteeList(InviteePageQuery pageQuery, Integer userId);

    /**
     * 更新用户资料
     *
     * @param request
     */
    void editUserProfileInfo(UserProfileInfoRequest request);

    /**
     * 修改密码
     *
     * @param request
     */
    void resetUserPwd(UserProfileInfoRequest request);

    /**
     * 手机号是否存在
     *
     * @param phone
     * @return
     */
    Boolean phoneExists(String phone);

    /**
     * 获取用户首页图片地址
     *
     * @param userId
     * @return
     */
    List<CosFileResourcePair> getUserExtraResources(Integer userId);

    /**
     * 重置用户头像
     * @param file
     * @param userId
     * @return
     */
    String resetUserAvatar(MultipartFile file, Integer userId);

    /**
     *
     * @param file
     * @param userId
     * @return
     */
    void uploadClientAvatar(MultipartFile file, Integer userId);
}

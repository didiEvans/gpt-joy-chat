package cloud.cstream.chat.client.controller;

import cloud.cstream.chat.client.domain.request.LoginFrontUserByEmailRequest;
import cloud.cstream.chat.client.domain.request.LoginFrontUserByPhoneRequest;
import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.domain.request.SendSmsCaptchaRequest;
import cloud.cstream.chat.client.domain.vo.LoginInfoVO;
import cloud.cstream.chat.client.domain.vo.RegisterCaptchaVO;
import cloud.cstream.chat.client.domain.vo.UserInfoVO;
import cloud.cstream.chat.client.service.ClientUserService;
import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.enums.LoginTypeEnum;
import cloud.cstream.chat.common.valid.ValidGroup;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cloud.cstream.chat.core.domain.query.InviteePageQuery;
import cloud.cstream.chat.core.domain.request.PhoneExistsRequest;
import cloud.cstream.chat.core.domain.request.UserProfileInfoRequest;
import cloud.cstream.chat.core.domain.vo.InviterInfoVO;
import cloud.cstream.chat.core.domain.vo.UserInviteeListVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * client-客户端用户相关接口
 *
 * @author Anker
 * @date 2023/4/25 16:34
 */
@AllArgsConstructor
@RestController
@RequestMapping("client/user")
public class ClientUserController {

    private final ClientUserService clientUserService;


    /**
     * 邮件验证回调
     *
     * @param code 邮箱验证码
     * @param virtualAlias 邀请码
     * @return
     */
    @GetMapping("/verify_email_code")
    public R<Void> verifyEmailCode(@RequestParam("code") String code, @RequestParam(value = "virtualAlias", required = false) String virtualAlias) {
        clientUserService.verifyCode(ClientUserRegisterTypeEnum.EMAIL, code, virtualAlias);
        return R.success("验证成功");
    }

    /**
     * 邮箱注册
     *
     * @param request 请求参数
     * @return
     */
    @PostMapping("/register/email")
    public R<Void> registerFrontUser(@Validated @RequestBody RegisterFrontUserRequest request) {
        clientUserService.register(request);
        return R.success("注册成功");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public R<UserInfoVO> getUserInfo() {
        return R.data(clientUserService.getLoginUserInfo());
    }

    /**
     * 获取图片验证码
     *
     * @return
     */
    @GetMapping("/get_pic_code")
    public R<RegisterCaptchaVO> getPictureVerificationCode() {
        return R.data(clientUserService.generateCaptcha());
    }

    /**
     * 邮箱登录
     * @param request
     * @return
     */
    @PostMapping("/login/email")
    public R<LoginInfoVO> login(@RequestBody LoginFrontUserByEmailRequest request) {
        return R.data(clientUserService.login(ClientUserRegisterTypeEnum.EMAIL, request.getUsername(), request.getPassword(), LoginTypeEnum.PWD.getType(), null, null));
    }


    /**
     * 手机号一键登录
     * @param request
     * @return
     */
    @PostMapping("/login/phone")
    public R<LoginInfoVO> registryAndLogin(@RequestBody LoginFrontUserByPhoneRequest request) {
        return R.data(clientUserService.login(ClientUserRegisterTypeEnum.PHONE, request.getUsername(), request.getPassword(), request.getLoginType(), request.getCaptcha(), request.getInviteCode()));
    }

    /**
     * 获取邀请人信息
     *
     * @return
     */
    @GetMapping("/inviter_info")
    public R<InviterInfoVO> getInviterInfo() {
        return R.data(clientUserService.getInviterInfo(ClientUserLoginUtil.getUserId()));
    }

    /**
     * 获取充值二维码
     *
     * @return
     */
    @GetMapping("rechargeQrCode")
    public R<String> getRechargeQrCode(){
        return R.data(clientUserService.getRechargeQrCode());
    }


    /**
     * 发送验证码
     *
     * @return
     */
    @PostMapping("send_captcha")
    public R<Void> sendCaptcha(@RequestBody @Validated SendSmsCaptchaRequest request){
        clientUserService.sendCaptcha(request);
        return R.success();
    }


    /**
     * 获取用户邀请列表
     *
     * @param pageQuery
     * @return
     */
    @PostMapping("get_invitee_list")
    public R<Page<UserInviteeListVO>> getUserInviteeList(@RequestBody InviteePageQuery pageQuery){
        return R.data(clientUserService.getUserInviteeList(pageQuery, ClientUserLoginUtil.getUserId()));
    }

    /**
     * 编辑个人资料
     *
     * @param request
     * @return
     */
    @PostMapping("edit_profile_info")
    public R<Void> editProfileInfo(@RequestBody @Validated(ValidGroup.Update.class)UserProfileInfoRequest request){
        clientUserService.editUserProfileInfo(request);
        return R.success();
    }

    /**
     * 重置密码
     *
     * @param request
     * @return
     */
    @PostMapping("reset_pwd")
    public R<Void> resetPwd(@RequestBody @Validated(ValidGroup.Add.class)UserProfileInfoRequest request){
        clientUserService.resetUserPwd(request);
        return R.success();
    }


    @PostMapping("reset_avatar")
    public R<String> resetUserAvatar(@RequestParam("file")MultipartFile file){
        return R.data(clientUserService.resetUserAvatar(file, ClientUserLoginUtil.getUserId()));
    }

    /**
     * 该号码用户是否存在
     *
     * @param request   请求号码
     * @return 是否存在
     */
    @PostMapping("Identify_exists")
    public R<Boolean> queryPhoneExists(@RequestBody @Validated PhoneExistsRequest request){
        return R.data(clientUserService.phoneExists(request.getIdentify()));
    }

    /**
     * 获取用户拓展资源
     *
     * @return 图片资源
     */
    @GetMapping("get_user_extra_resources")
    public R<List<CosFileResourcePair>> getUserExtraResources(){
        return R.data(clientUserService.getUserExtraResources(ClientUserLoginUtil.getUserId()));
    }

    /**
     * 上传用户头像
     *
     * @param file  文件
     * @return 文件地址
     */
    @PostMapping("upload_user_avatar")
    public R<String> uploadClientAvatar(@RequestParam("file")MultipartFile file){
        clientUserService.uploadClientAvatar(file, ClientUserLoginUtil.getUserId());
        return R.success();
    }
}

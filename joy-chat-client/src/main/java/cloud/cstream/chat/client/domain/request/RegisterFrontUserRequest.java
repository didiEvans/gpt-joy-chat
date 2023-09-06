package cloud.cstream.chat.client.domain.request;

import cloud.cstream.chat.client.handlers.validation.annotation.FrontUserRegisterAvailable;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端用户注册请求，适用于邮箱登录登录
 *
 * @author CoDeleven
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FrontUserRegisterAvailable
public class RegisterFrontUserRequest {
    /**
     * 用户ID，可以为邮箱，可以为手机号
     */
    @Size(min = 6, max = 64, message = "用户名长度应为6~64个字符")
    @NotNull
    private String identity;

    /**
     * 密码
     * TODO 正则校验
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 图形验证码会话 ID
     */
    @NotNull(message = "验证码会话 ID 不能为空")
    private String picCodeSessionId;

    /**
     * 图片验证码
     */
    @NotNull(message = "图片验证码不能为空")
    private String picVerificationCode;
    /**
     * 手机验证码
     */
    @NotNull(message = "验证码不能为空")
    private String captcha;

    /**
     * 邀请码
     */
    private String inviteCode;

    private ClientUserRegisterTypeEnum registerType = ClientUserRegisterTypeEnum.EMAIL;
}
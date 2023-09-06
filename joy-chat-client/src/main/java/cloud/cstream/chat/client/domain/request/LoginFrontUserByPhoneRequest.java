package cloud.cstream.chat.client.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 前端用户登录请求
 *
 * @author CoDeleven
 */
@Data
public class LoginFrontUserByPhoneRequest {
    /**
     * 邮箱地址
     */
    @NotNull(message = "请输入手机号")
    private String username;
    /**
     * 1 密码登录, 2 验证码登录
     */
    @NotNull(message = "未知的登录方式")
    private Integer loginType;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 邀请码
     */
    private String inviteCode;
}

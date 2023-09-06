package cloud.cstream.chat.client.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 前端用户登录请求
 *
 * @author CoDeleven
 */
@Data
public class LoginFrontUserByEmailRequest {
    /**
     * 邮箱地址
     */
    @NotNull(message = "请输入邮箱地址")
    @Email(message = "邮箱地址格式错误")
    private String username;

    /**
     * 密码
     */
    private String password;
}

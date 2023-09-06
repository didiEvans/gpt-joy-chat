package cloud.cstream.chat.core.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Anker
 * @date 2023/3/28 09:56
 * 系统用户登录参数
 */
@Data
public class SysUserLoginRequest {
    /**
     * 账号
     */
    @NotNull(message = "账号不能为空")
    private String account;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
}

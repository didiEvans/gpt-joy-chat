package cloud.cstream.chat.client.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Data
public class SendSmsCaptchaRequest {

    @NotBlank(message = "手机号不能为空")
    private String phone;

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

}

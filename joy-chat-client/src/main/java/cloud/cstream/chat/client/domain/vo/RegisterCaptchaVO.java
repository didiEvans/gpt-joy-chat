package cloud.cstream.chat.client.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每次注册前需要获取图形验证码信息
 *
 * @author CoDeleven
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterCaptchaVO {

    /**
     * 图形验证码会话ID，注册时需要传过来
     */
    private String picCodeSessionId;

    /**
     * 图形验证码Base64
     */
    private String picCodeBase64;
}

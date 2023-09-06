package cloud.cstream.chat.client.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功后返回前端登录结果
 *
 * @author CoDeleven
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginInfoVO {

    /**
     * 登录的Token
     */
    private String token;

    /**
     * 基础用户ID
     */
    private Integer baseUserId;
}

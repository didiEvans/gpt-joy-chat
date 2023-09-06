package cloud.cstream.chat.client.domain.bo;

import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import lombok.Data;

/**
 * @author Anker
 * @date 2023/4/16 17:44
 * JWT 用户信息业务参数
 */
@Data
public class JwtUserInfoBO {

    /**
     * 注册类型
     */
    private ClientUserRegisterTypeEnum registerType;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 基础用户 id
     */
    private Integer userId;

    /**
     * 扩展用户 id
     */
    private Integer extraUserId;
}

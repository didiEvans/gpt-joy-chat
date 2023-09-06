package cloud.cstream.chat.core.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/5/20
 */
@Data
@Builder
public class SysUserVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;


}

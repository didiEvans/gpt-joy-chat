package cloud.cstream.chat.core.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/5/8
 */
@Data
@Builder
public class InviterInfoVO {


    /**
     * 邀请人链接
     */
    private String inviteUrl;

    /**
     * 邀请码
     */
    private String inviteCode;

}

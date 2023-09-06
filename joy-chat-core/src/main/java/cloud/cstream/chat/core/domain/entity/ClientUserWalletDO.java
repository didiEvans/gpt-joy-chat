package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("client_user_wallet")
public class ClientUserWalletDO {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 用户积分
     */
    private Integer points;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

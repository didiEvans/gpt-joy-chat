package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@TableName("client_user_wallet_usage_record")
public class ClientUserWalletUsageRecordDO {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 消耗金额
     */
    private BigDecimal amount;
    /**
     * 金额类型，1表示积分
     */
    private Integer amountType;
    /**
     * 方向，1表示入账，-1表示出账
     */
    private Integer direction;
    /**
     * 备注
     */
    private String remark;
    /**
     * 业务类型，1表示会话，2表示绘图
     */
    private Integer bizType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    // getter和setter方法
    // ...
}

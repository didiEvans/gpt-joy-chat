package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 增值服务套餐充值记录
 *
 * @author evans
 * @description
 * @date 2023/5/15
 */
@Data
@TableName("vas_recharge_record")
public class VasRechargeRecordDO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 会员套餐id
     */
    private Integer vasPkgId;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 关联订单id
     */
    private  Integer orderId;
    /**
     * 会员过期时间
     */
    private Date expireTime;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

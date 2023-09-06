package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 增值服务订单表
 *
 * @author Anker
 */
@Data
@TableName("vas_order")
public class VasOrderDO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 本地订单号
     */
    private String orderNo;
    /**
     * 三方支付订单号
     */
    private String outTradNo;
    /**
     * 支付方式 1 线下支付, 2 微信支付, 3 支付宝支付
     */
    private Integer paymentMode;
    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;
    /**
     * 交易时间
     */
    private Date transactionTime;
    /**
     * 交易类型 入账/ 出账
     */
    private Integer transactionType;
    /**
     * 支付状态 0 未支付, 1 支付成功, 2 支付失败
     */
    private Integer payStatus;
    /**
     * 购买用户id
     */
    private Integer uid;
    /**
     * 增值服务id
     */
    private Integer vasId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

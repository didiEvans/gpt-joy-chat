package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VasOrderPageVO {


    private Integer id;
    /**
     * 本地订单号
     */
    private String orderNo;
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
    private String username;
    /**
     * 增值服务id
     */
    private Integer vasId;
    /**
     * 增值服务名称
     */
    private String vasPkgName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

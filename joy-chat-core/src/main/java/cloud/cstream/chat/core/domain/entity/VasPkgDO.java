package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 增值服务套餐
 * @author Anker
 */
@Data
@TableName("vas_pkg")
public class VasPkgDO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 增值服务唯一标识
     */
    private String udid;
    /**
     * 增值服务名称
     */
    private String vasName;
    /**
     * 增值服务售价
     */
    private BigDecimal vasPrice;
    /**
     * 增值服务原价
     */
    private BigDecimal vasCost;
    /**
     * 增值服务描述
     */
    private String vasDescribe;
    /**
     * 时间区间限制
     */
    private Integer confTimeInterval;
    /**
     * 时间区间内限制次数
     */
    private Integer confLimit;
    /**
     * 积分值
     */
    private Integer points;
    /**
     * 1 chat, 2 draw
     */
    private Integer type;
    /**
     * 商品地址
     */
    private String productUrl;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 0 启用， 1 禁用
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
}

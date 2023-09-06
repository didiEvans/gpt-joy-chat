package cloud.cstream.chat.core.domain.request;

import cloud.cstream.chat.common.valid.ValidGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VasPkgRequest {

    @NotNull(message = "id 不能为空", groups = {ValidGroup.Update.class, ValidGroup.Del.class})
    private Integer id;
    /**
     * 增值服务名称
     */
    @NotBlank(message = "套餐包名称不能为空", groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private String vasName;
    /**
     * 增值服务售价
     */
    @NotNull(message = "请设置售价", groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    @Min(value = 1, message = "售价不能低于1元", groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private BigDecimal vasPrice;
    /**
     * 增值服务原价
     */
    @NotNull(message = "请设置原价", groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    @Min(value = 1, message = "原价不能低于1元", groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private BigDecimal vasCost;

    /**
     * 商品地址
     */
    @NotBlank(message = "请设置商品地址", groups = {ValidGroup.Add.class, ValidGroup.Update.class})
    private String productUrl;
    /**
     * 增值服务描述
     */
    @NotBlank(message = "描述不能为空")
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
    @NotNull(message = "积分不能为空")
    private Integer points;
    /**
     * 1 chat, 2 draw
     */
    @NotNull(message = "请标识套餐类型")
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;

}

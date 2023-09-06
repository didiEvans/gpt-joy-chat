package cloud.cstream.chat.core.domain.request;

import cloud.cstream.chat.common.valid.ValidGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 卡密对请求参数
 *
 * @author evans
 * @description
 * @date 2023/5/24
 */
@Data
public class CardPairRequest {

    /**
     * 卡密对个数
     */
    @NotNull(message = "请输入生成卡密对个数", groups = ValidGroup.Add.class)
    @Max(500)
    @Min(1)
    private Integer size;
    /**
     * 对应的套餐服务
     */
    @NotNull(message = "请指定卡密对关联的套餐", groups = ValidGroup.Add.class)
    private Integer vasPkgId;

}

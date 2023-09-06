package cloud.cstream.chat.core.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/5/24
 */
@Data
public class CardPairConvertRequest {


    @NotNull(message = "请输入您购买的卡密")
    private String cardPwd;

    @NotNull(message = "请输入购买的卡号")
    private String cardKey;

}

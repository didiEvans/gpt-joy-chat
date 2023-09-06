package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author evans
 * @description
 * @date 2023/5/7
 */
@Getter
@AllArgsConstructor
public enum PaymentModeEnum {
    /**
     * 手动
     */
    MANUAL(1, "手动开通"),
    /**
     * 微信支付
     */
    WECHAT_PAY(2, "微信支付"),
    /**
     * 支付宝
     */
    ALI_PAY(3, "支付宝"),


    CARD_ISSUING(4, "发卡购买"),
    ;

    private final Integer type;

    private final String describe;
}

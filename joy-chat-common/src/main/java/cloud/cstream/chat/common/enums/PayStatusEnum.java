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
public enum PayStatusEnum {


    UNPAID(0, "未支付"),

    SUCCESS(1, "支付成功"),

    FAILED(2, "支付失败")
    ;



    private final Integer code;

    private final String describe;


}

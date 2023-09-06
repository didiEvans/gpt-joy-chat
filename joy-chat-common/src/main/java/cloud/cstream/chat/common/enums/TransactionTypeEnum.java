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
public enum TransactionTypeEnum {


    INCOME(0, "入账"),

    EXPEND(1, "出账"),

    ;



    private final Integer code;

    private final String describe;


}

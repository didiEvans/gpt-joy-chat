package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BoolEnum {
    /**
     * FALSE
     */
    FALSE(0, Boolean.FALSE),
    /**
     * TRUE
     */
    TRUE(1, Boolean.TRUE)
    ;


    private final Integer var;

    private final Boolean map;


    public static BoolEnum getByValue(Integer value) {
        return Arrays.stream(values()).filter(b -> b.var.equals(value)).findFirst().orElse(null);
    }

    public static Boolean isTrue(Integer var) {
        return BoolEnum.TRUE.equals(getByValue(var));
    }

    public static Boolean isFalse(Integer var) {
        return !isTrue(var);
    }

}

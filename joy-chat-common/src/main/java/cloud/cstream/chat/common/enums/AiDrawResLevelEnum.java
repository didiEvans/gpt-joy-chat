package cloud.cstream.chat.common.enums;

import cloud.cstream.chat.common.constants.NumConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AiDrawResLevelEnum {


    LEVEL_0(0, 0, "普通"),
    LEVEL_1(1, 1, "高清"),
    LEVEL_2(2, 4, "精绘"),
    LEVEL_3(3, 15, "超精绘");


    private final Integer code;


    private final Integer expendPoints;


    private final String describe;


    public static AiDrawResLevelEnum load(Integer code){
        return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findAny().orElse(LEVEL_0);
    }

    public static Integer compute(Integer code){
        return load(code).getExpendPoints() + NumConstant.INT_ONE;
    }

}

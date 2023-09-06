package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * ai绘画处理器枚举
 *
 * @author Anker
 */
@Getter
@AllArgsConstructor
public enum AiDrawHandlerEnum {

    YiJAiDraw(10, "意间AI")
    ;

    private final Integer handlerCode;


    private final String describe;


    public static AiDrawHandlerEnum loadByCode(Integer handlerCode){
        return Arrays.stream(values()).filter(el -> el.getHandlerCode().equals(handlerCode)).findAny().orElse(YiJAiDraw);
    }
}

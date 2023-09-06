package cloud.cstream.chat.common.enums;

import cloud.cstream.chat.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizType {

    /**
     * chatGPT
     */
    CHAT(1, "chatGPT"),
    /**
     * AI绘图
     */
    DRAW(2, "绘图")
    ;



    private final Integer type;


    private final String name;


    public static  BizType getByCode(Integer code) {
        return Arrays.stream(values()).filter(v -> v.getType().equals(code)).findFirst().orElseThrow(() -> new ServiceException("未知业务风险"));
    }

    public static boolean isChatBiz(Integer code) {
        return CHAT.equals(getByCode(code));
    }

    public static boolean isDrawBiz(Integer code) {
        return DRAW.equals(getByCode(code));
    }

}

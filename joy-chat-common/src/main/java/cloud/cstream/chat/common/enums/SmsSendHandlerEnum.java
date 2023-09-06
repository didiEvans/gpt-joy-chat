package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Getter
@AllArgsConstructor
public enum SmsSendHandlerEnum {

    /**
     * 腾讯云
     */
    TENCENT(10, "腾讯云")
    ;



    private final Integer handlerCode;

    private final String describe;

}

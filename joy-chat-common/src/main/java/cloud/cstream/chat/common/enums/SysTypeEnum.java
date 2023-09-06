package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysTypeEnum {

    /**
     * 管理员
     */
    SYS(0),
    /**
     * 客户端
     */
    CLIENT(1);


    private final Integer code;

}

package cloud.cstream.chat.common.enums;

import cloud.cstream.chat.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 密码登录
     */
    PWD(1, "密码登录"),
    /**
     * 验证码登录
     */
    CODE(2, "验证码登录")
    ;

    private final Integer type;

    private final String desc;


    public static LoginTypeEnum load(Integer type){
        return Arrays.stream(values()).filter(e -> e.getType().equals(type)).findFirst().orElse(null);
    }

}

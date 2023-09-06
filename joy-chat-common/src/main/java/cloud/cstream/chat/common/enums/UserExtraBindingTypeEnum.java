package cloud.cstream.chat.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户绑定类型枚举
 * 扩展手机号、微信等
 *
 * @author CoDeleven
 */
@AllArgsConstructor
@Getter
public enum UserExtraBindingTypeEnum {

    /**
     * 邮箱
     */
    BIND_EMAIL("email", "邮箱绑定"),
    /**
     * 手机号绑定
     */
    BIND_PHONE("phone", "手机号绑定");

    @EnumValue
    private final String code;

    private final String desc;
}

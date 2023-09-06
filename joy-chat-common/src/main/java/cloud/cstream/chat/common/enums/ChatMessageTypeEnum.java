package cloud.cstream.chat.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Anker
 * @date 2023/3/26 01:53
 * 聊天消息类型枚举
 */
@AllArgsConstructor
public enum ChatMessageTypeEnum {

    /**
     * 问题
     */
    QUESTION(1),

    /**
     * 回答
     */
    ANSWER(2);

    @Getter
    @EnumValue
    private final Integer code;
}

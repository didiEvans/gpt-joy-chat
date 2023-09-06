package cloud.cstream.chat.core.domain.vo;

import cloud.cstream.chat.common.enums.ApiTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/3/27 23:21
 * 聊天室展示参数
 */
@Data
public class ChatRoomVO {

    /**
     * 聊天室 id
     */
    private Long id;

    /**
     * 对话 id 唯一
     */
    private String conversationId;

    /**
     * ip
     */
    private String ip;

    /**
     * 第一条消息 id
     */
    private String firstMessageId;

    /**
     * 对话标题，从第一条消息截取
     */
    private String title;
    /**
     * API 类型
     * 不同类型的对话不能一起存储
     */
    private ApiTypeEnum apiType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

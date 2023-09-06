package cloud.cstream.chat.client.api.storage;

import lombok.Builder;
import lombok.Data;

/**
 * @author Anker
 * @date 2023/3/25 17:00
 * 聊天回复的通用信息
 * 基本用于 AccessToken
 */
@Data
@Builder
public class ChatReplyCommonStorage {

    /**
     * 角色
     */
    private String role;

    /**
     * 对话 id
     */
    private String conversationId;

    /**
     * 消息 id
     */
    private String messageId;
}

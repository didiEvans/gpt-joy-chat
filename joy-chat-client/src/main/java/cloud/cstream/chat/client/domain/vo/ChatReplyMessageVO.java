package cloud.cstream.chat.client.domain.vo;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import lombok.Data;

import java.util.Optional;

/**
 * @author Anker
 * @date 2023/3/23 13:53
 * 聊天回复的消息
 */
@Data
public class ChatReplyMessageVO {

    /**
     * 角色
     * 对于前端有什么用？
     */
    private String role;
    /**
     * 当前消息id
     */
    private String id;
    /**
     * 父级消息 id
     */
    private String parentMessageId;
    /**
     * 对话 id
     */
    private String conversationId;
    /**
     * 回复的消息
     */
    private String text;

    /**
     * 当链路出现问题时 取上一条消息的 parentMessageId 和 conversationId，使得异常不影响上下文
     *
     * @param request 消息处理请求的实体 从中获取 parentMessageId 和 conversationId
     * @return 聊天回复的消息
     */
    public static ChatReplyMessageVO onEmitterChainException(ChatProcessRequest request) {
        ChatProcessRequest.Options options = request.getOptions();
        ChatReplyMessageVO chatReplyMessageVO = new ChatReplyMessageVO();
        chatReplyMessageVO.setId(Optional.of(options).orElse(new ChatProcessRequest.Options()).getParentMessageId());
        chatReplyMessageVO.setConversationId(Optional.of(options).orElse(new ChatProcessRequest.Options()).getConversationId());
        chatReplyMessageVO.setParentMessageId(null);
        return chatReplyMessageVO;
    }
}

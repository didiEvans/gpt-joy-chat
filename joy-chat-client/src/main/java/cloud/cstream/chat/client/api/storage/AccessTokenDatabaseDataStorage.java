package cloud.cstream.chat.client.api.storage;


import cloud.cstream.chat.client.api.accesstoken.ConversationResponse;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import org.springframework.stereotype.Component;

/**
 * @author Anker
 * @date 2023/3/25 23:59
 * AccessToken 数据库数据存储
 */
@Component
public class AccessTokenDatabaseDataStorage extends AbstractDatabaseDataStorage {

    @Override
    public void onFirstMessage(ChatMessageStorage chatMessageStorage) {
        // 第一条消息
        ConversationResponse conversationResponse = (ConversationResponse) chatMessageStorage.getParser().
                parseSuccess(chatMessageStorage.getOriginalResponseData());
        ConversationResponse.Message message = conversationResponse.getMessage();

        // 第一条消息填充对话 id 和消息 id
        ChatMessageDO answerChatMessageDO = chatMessageStorage.getAnswerChatMessageDO();
        answerChatMessageDO.setMessageId(message.getId());
        answerChatMessageDO.setConversationId(conversationResponse.getConversationId());

        // 填充问题消息的对话 id
        chatMessageStorage.getQuestionChatMessageDO().setConversationId(conversationResponse.getConversationId());
    }

    @Override
    void onLastMessage(ChatMessageStorage chatMessageStorage) {

    }

    @Override
    void onErrorMessage(ChatMessageStorage chatMessageStorage) {

    }
}

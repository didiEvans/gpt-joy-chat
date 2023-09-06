package cloud.cstream.chat.client.domain.dto;

import com.unfbx.chatgpt.entity.chat.Message;
import lombok.Data;

import java.util.List;

/**
 * 消息优化
 *
 * @author Anker
 */
@Data
public class MessageOptimizeDTO {


    /**
     * 优化后的消息集合
     */
    private List<Message> messages;

    /**
     * 消息集合token数量
     */
    private Integer totalMsgTokens;

}

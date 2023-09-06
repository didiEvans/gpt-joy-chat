package cloud.cstream.chat.core.domain.query;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Anker
 * @date 2023/3/27 23:14
 * 聊天记录分页查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatMessagePageQuery extends PageQuery {

    /**
     * 聊天室 id
     */
    private Long chatRoomId;
    /**
     * ip 地址
     */
    @Size(max = 20, message = "ip 字数不能超过 20")
    private String ip;
    /**
     * 问题或回复模糊搜索
     */
    @Size(max = 20, message = "问题或回复搜索字数字数不能超过20")
    private String content;



    private String sendUserName;
}

package cloud.cstream.chat.core.domain.query;

import cloud.cstream.chat.core.domain.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Anker
 * @date 2023/3/27 23:18
 * 聊天记录分页查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomPageQuery extends PageQuery {
}

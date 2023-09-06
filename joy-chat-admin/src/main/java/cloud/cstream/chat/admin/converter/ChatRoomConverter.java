package cloud.cstream.chat.admin.converter;



import cloud.cstream.chat.core.domain.vo.ChatRoomVO;
import cloud.cstream.chat.core.domain.entity.ChatRoomDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/27 23:25
 * 聊天室相关转换
 */
@Mapper
public interface ChatRoomConverter {

    ChatRoomConverter INSTANCE = Mappers.getMapper(ChatRoomConverter.class);

    /**
     * entityToVO
     *
     * @param chatRoomDOList chatRoomDOList
     * @return List<ChatRoomVO>
     */
    List<ChatRoomVO> entityToVO(List<ChatRoomDO> chatRoomDOList);
}

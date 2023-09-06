package cloud.cstream.chat.admin.converter;

import cloud.cstream.chat.core.domain.vo.ChatMessageVO;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/28 12:39
 * 聊天记录相关转换
 */
@Mapper
public interface ChatMessageConverter {

    ChatMessageConverter INSTANCE = Mappers.getMapper(ChatMessageConverter.class);

    /**
     * entityToVO
     *
     * @param chatMessageDOList chatMessageDOList
     * @return List<ChatMessageVO>
     */
    List<ChatMessageVO> entityToVO(List<ChatMessageDO> chatMessageDOList);
}

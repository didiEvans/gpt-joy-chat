package cloud.cstream.chat.client.service;

import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import cloud.cstream.chat.core.domain.entity.ChatRoomDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Anker
 * @date 2023/3/25 16:30
 * 聊天室相关业务接口
 */
public interface ChatRoomService extends IService<ChatRoomDO> {

    /**
     * 创建聊天室
     *
     * @param chatMessageDO 聊天记录
     * @return 聊天室
     */
    ChatRoomDO createChatRoom(ChatMessageDO chatMessageDO);
}

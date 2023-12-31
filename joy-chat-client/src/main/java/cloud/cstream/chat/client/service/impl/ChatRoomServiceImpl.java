package cloud.cstream.chat.client.service.impl;

import cloud.cstream.chat.client.service.ChatRoomService;
import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import cloud.cstream.chat.core.domain.entity.ChatRoomDO;
import cloud.cstream.chat.core.mapper.ChatRoomMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/3/25 16:31
 * 聊天室相关业务实现类
 */
@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoomDO> implements ChatRoomService {

    @Override
    public ChatRoomDO createChatRoom(ChatMessageDO chatMessageDO) {
        ChatRoomDO chatRoom = new ChatRoomDO();
        chatRoom.setId(IdWorker.getId());
        chatRoom.setApiType(chatMessageDO.getApiType());
        chatRoom.setIp(WebUtil.getIp());
        chatRoom.setFirstChatMessageId(chatMessageDO.getId());
        chatRoom.setFirstMessageId(chatMessageDO.getMessageId());
        // 取一部分内容当标题
        chatRoom.setTitle(StrUtil.sub(chatMessageDO.getContent(), 0, 50));
        chatRoom.setCreateTime(new Date());
        chatRoom.setUpdateTime(new Date());
        chatRoom.setUserId(ClientUserLoginUtil.getUserId());
        save(chatRoom);
        return chatRoom;
    }
}

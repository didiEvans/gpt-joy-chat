package cloud.cstream.chat.admin.service.impl;

import cloud.cstream.chat.core.domain.query.ChatRoomPageQuery;
import cloud.cstream.chat.core.domain.vo.ChatRoomVO;
import cloud.cstream.chat.admin.converter.ChatRoomConverter;
import cloud.cstream.chat.admin.service.ChatRoomService;
import cloud.cstream.chat.common.util.PageUtil;
import cloud.cstream.chat.core.domain.entity.ChatRoomDO;
import cloud.cstream.chat.core.mapper.ChatRoomMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * @author Anker
 * @date 2023/3/27 21:46
 * 聊天室业务实现类
 */
@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoomDO> implements ChatRoomService {

    @Override
    public IPage<ChatRoomVO> pageChatRoom(ChatRoomPageQuery chatRoomPageQuery) {
        Page<ChatRoomDO> chatRoomPage = page(new Page<>(chatRoomPageQuery.getPageNum(), chatRoomPageQuery.getPageSize()), new LambdaQueryWrapper<ChatRoomDO>()
                .orderByDesc(ChatRoomDO::getId));

        return PageUtil.toPage(chatRoomPage, ChatRoomConverter.INSTANCE.entityToVO(chatRoomPage.getRecords()));
    }
}

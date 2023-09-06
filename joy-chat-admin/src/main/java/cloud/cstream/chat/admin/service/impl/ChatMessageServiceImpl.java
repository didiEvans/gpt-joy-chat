package cloud.cstream.chat.admin.service.impl;

import cloud.cstream.chat.admin.service.ChatMessageService;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import cloud.cstream.chat.core.domain.query.ChatMessagePageQuery;
import cloud.cstream.chat.core.domain.vo.ChatMessageVO;
import cloud.cstream.chat.core.mapper.ChatMessageMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Anker
 * @date 2023/3/27 21:46
 * 聊天记录业务实现类
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessageDO> implements ChatMessageService {

    @Override
    public IPage<ChatMessageVO> pageChatMessage(ChatMessagePageQuery pageQuery) {
        return baseMapper.queryPage(pageQuery.toPage(), pageQuery);
    }
}

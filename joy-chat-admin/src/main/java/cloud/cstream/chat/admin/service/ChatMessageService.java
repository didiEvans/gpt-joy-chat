package cloud.cstream.chat.admin.service;

import cloud.cstream.chat.core.domain.query.ChatMessagePageQuery;
import cloud.cstream.chat.core.domain.vo.ChatMessageVO;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Anker
 * @date 2023/3/27 21:46
 * 聊天记录相关业务接口
 */
public interface ChatMessageService extends IService<ChatMessageDO> {

    /**
     * 聊天记录分页
     *
     * @param chatMessagePageQuery 查询参数
     * @return 聊天记录展示参数
     */
    IPage<ChatMessageVO> pageChatMessage(ChatMessagePageQuery chatMessagePageQuery);
}

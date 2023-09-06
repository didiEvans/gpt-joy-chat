package cloud.cstream.chat.admin.service;

import cloud.cstream.chat.core.domain.query.ChatRoomPageQuery;
import cloud.cstream.chat.core.domain.vo.ChatRoomVO;
import cloud.cstream.chat.core.domain.entity.ChatRoomDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Anker
 * @date 2023/3/27 21:45
 * 聊天室相关业务接口
 */
public interface ChatRoomService extends IService<ChatRoomDO> {


    /**
     * 聊天室分页
     *
     * @param chatRoomPageQuery 查询参数
     * @return 聊天室展示参数
     */
    IPage<ChatRoomVO> pageChatRoom(ChatRoomPageQuery chatRoomPageQuery);
}

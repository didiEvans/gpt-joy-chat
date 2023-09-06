package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.core.domain.query.ChatRoomPageQuery;
import cloud.cstream.chat.core.domain.vo.ChatRoomVO;
import cloud.cstream.chat.admin.service.ChatRoomService;
import cloud.cstream.chat.common.domain.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin-聊天室相关接口
 *
 * @author Anker
 * @date 2023/3/27 23:13
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/chat_room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 聊天室分页列表
     * @param chatRoomPageQuery
     * @return
     */
    @PostMapping("/page")
    public R<IPage<ChatRoomVO>> page(@Validated @RequestBody ChatRoomPageQuery chatRoomPageQuery) {
        return R.data(chatRoomService.pageChatRoom(chatRoomPageQuery));
    }
}

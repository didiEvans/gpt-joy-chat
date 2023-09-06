package cloud.cstream.chat.admin.controller;


import cloud.cstream.chat.admin.service.ChatMessageService;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.domain.query.ChatMessagePageQuery;
import cloud.cstream.chat.core.domain.vo.ChatMessageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin-聊天记录相关接口
 *
 * @author Anker
 * @date 2023/3/27 21:39
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/chat_message")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    /**
     * 记录分页列表
     *
     * @param pageQuery
     * @return
     */
    @PostMapping("/page")
    public R<IPage<ChatMessageVO>> page(@Validated @RequestBody ChatMessagePageQuery pageQuery) {
        return R.data(chatMessageService.pageChatMessage(pageQuery));
    }
}

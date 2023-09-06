package cloud.cstream.chat.client.controller;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.client.service.ChatMessageService;
import cloud.cstream.chat.core.domain.dto.SysPromptInfo;
import cloud.cstream.chat.core.service.PromptLibService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * client-对话接口
 *
 * @author YBin
 * @date 2023/4/25 16:44
 */
@AllArgsConstructor
@RestController
@RequestMapping("client/user")
public class OpenAiChatClientController {


    private final ChatMessageService chatMessageService;

    private final PromptLibService promptLibService;

    /**
     * ai对话聊天
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("send")
    public ResponseBodyEmitter sendMessage(@RequestBody @Validated ChatProcessRequest request, HttpServletResponse response) {
        SysPromptInfo sysPromptInfo = promptLibService.getSysPromptInfo(request.getSysPromptLibId());
        request.setSystemMessage(sysPromptInfo.getSysPromptContent());
        request.setReviewSysPrompt(sysPromptInfo.isReviewSysPrompt());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return chatMessageService.sendMessage(request);
    }



}

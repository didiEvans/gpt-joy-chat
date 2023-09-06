package cloud.cstream.chat.client.service;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

public interface ChatMessageService extends IService<ChatMessageDO> {
    /**
     * 收到用户问题, 准备调用chatGPT接口
     *
     * @param chatProcessRequest    请求参数
     * @return {@link  ResponseBodyEmitter} 事件消息
     */
    ResponseBodyEmitter sendMessage(ChatProcessRequest chatProcessRequest);

    /**
     * 初始化聊天消息
     *
     * @param chatProcessRequest 消息处理请求参数
     * @param apiTypeEnum        API 类型
     * @return 聊天消息
     */
    ChatMessageDO initChatMessage(ChatProcessRequest chatProcessRequest, ApiTypeEnum apiTypeEnum);
}

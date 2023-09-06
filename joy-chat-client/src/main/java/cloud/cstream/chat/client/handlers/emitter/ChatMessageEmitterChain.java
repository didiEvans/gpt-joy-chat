package cloud.cstream.chat.client.handlers.emitter;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.core.config.ChatConfig;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @author Anker
 * @date 2023/3/29 12:38
 * 正常发送消息链路，最后一个节点
 */
public class ChatMessageEmitterChain extends AbstractResponseEmitterChain {

    @Override
    public void doChain(ChatProcessRequest request, ResponseBodyEmitter emitter) {
        ApiTypeEnum apiTypeEnum = SpringUtil.getBean(ChatConfig.class).getApiTypeEnum();
        ResponseEmitter responseEmitter;
        if (apiTypeEnum == ApiTypeEnum.API_KEY) {
            responseEmitter = SpringUtil.getBean(ApiKeyResponseEmitter.class);
        } else {
            responseEmitter = SpringUtil.getBean(AccessTokenResponseEmitter.class);
        }
        responseEmitter.requestToResponseEmitter(request, emitter);
    }
}

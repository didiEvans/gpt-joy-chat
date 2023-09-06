package cloud.cstream.chat.client.handlers.emitter;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.client.domain.vo.ChatReplyMessageVO;
import cloud.cstream.chat.common.util.ObjectMapperUtil;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.core.handler.RateLimiterHandler;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

/**
 * @author lizhongyuan
 * Ip 限流处理
 */
@AllArgsConstructor
public class IpRateLimiterEmitterChain extends AbstractResponseEmitterChain {

    @Override
    public void doChain(ChatProcessRequest request, ResponseBodyEmitter emitter) {
        try {
            String ip = WebUtil.getIp();
            // 根据ip判断是够可放行
            Pair<Boolean, String> limitPair = RateLimiterHandler.allowRequest(ip);
            if (limitPair.getKey()) {
                if (getNext() != null) {
                    getNext().doChain(request, emitter);
                }
            } else {
                ChatReplyMessageVO chatReplyMessageVO = ChatReplyMessageVO.onEmitterChainException(request);
                chatReplyMessageVO.setText(StrUtil.format("当前访问人数过多，请等到 {} 后再尝试下", limitPair.getValue()));
                emitter.send(ObjectMapperUtil.toJson(chatReplyMessageVO));
                emitter.complete();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

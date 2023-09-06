package cloud.cstream.chat.client.handlers.emitter;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @author evans
 * @description
 * @date 2023/7/9
 */
public class ChatProcessFilterChain extends AbstractResponseEmitterChain {

    private final ResponseEmitterChain first;

    public ChatProcessFilterChain(ResponseEmitterChain... chains) {
        if (chains.length == 0) {
            throw new IllegalArgumentException("At least one chain is required");
        }
        this.first = chains[0];
        for (int i = 0; i < chains.length - 1; i++) {
            chains[i].setNext(chains[i + 1]);
        }
    }

    @Override
    public void doChain(ChatProcessRequest request, ResponseBodyEmitter emitter) {
        first.doChain(request, emitter);
    }

}
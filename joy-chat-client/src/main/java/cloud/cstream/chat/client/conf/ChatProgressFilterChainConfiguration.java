package cloud.cstream.chat.client.conf;

import cloud.cstream.chat.client.handlers.emitter.ChatMessageEmitterChain;
import cloud.cstream.chat.client.handlers.emitter.ChatProcessFilterChain;
import cloud.cstream.chat.client.handlers.emitter.IpRateLimiterEmitterChain;
import cloud.cstream.chat.client.handlers.emitter.SensitiveWordEmitterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author evans
 * @description
 * @date 2023/7/9
 */
@Configuration
public class ChatProgressFilterChainConfiguration {


    @Bean
    public ChatProcessFilterChain chatProcessFilterChain(){
        return new ChatProcessFilterChain(ipRateLimiterEmitterChain(), sensitiveWordEmitterChain(), chatMessageEmitterChain());
    }

    @Bean
    public IpRateLimiterEmitterChain ipRateLimiterEmitterChain() {
        return new IpRateLimiterEmitterChain();
    }

    @Bean
    public SensitiveWordEmitterChain sensitiveWordEmitterChain() {
        return new SensitiveWordEmitterChain();
    }

    @Bean
    public ChatMessageEmitterChain chatMessageEmitterChain() {
        return new ChatMessageEmitterChain();
    }

}

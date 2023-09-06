package cloud.cstream.chat.core.config;

import cloud.cstream.chat.core.handler.serializer.DateFormatterSerializer;
import cloud.cstream.chat.core.handler.serializer.LongToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/4/1 03:34
 * Jackson 配置
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        // 返回 Long 转 String
        builder.serializerByType(Long.class, new LongToStringSerializer());
        // 返回 Date 格式化
        builder.serializerByType(Date.class, new DateFormatterSerializer());
        return builder;
    }
}
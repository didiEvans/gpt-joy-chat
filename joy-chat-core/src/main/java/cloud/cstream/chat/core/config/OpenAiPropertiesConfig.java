package cloud.cstream.chat.core.config;

import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;
import cloud.cstream.chat.core.mapper.OpenaiChatConfigMapper;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

@Configuration
public class OpenAiPropertiesConfig {

    @Autowired
    private OpenaiChatConfigMapper openaiChatConfigMapper;

    @Bean
    @ConditionalOnMissingBean
    public ChatConfig chatConfig(){
        OpenaiChatConfigDO chatConfigDO = openaiChatConfigMapper.selectOne(Wrappers.lambdaQuery());
        if (Objects.isNull(chatConfigDO)){
            throw new RuntimeException("please set you open ai chatGPT config properties");
        }
        if (CharSequenceUtil.isBlankOrUndefined(chatConfigDO.getApiKeys())){
            throw new RuntimeException("please set you open ai chatGPT api keys properties");
        }
        List<String> apiKeys = JSONUtil.toList(chatConfigDO.getApiKeys(), String.class);

        return ChatConfig.builder()
                .warringEmail(JSONUtil.toList(chatConfigDO.getWarringEmail(), String.class))
                // 几乎用不到accessToken
                .accessToken(chatConfigDO.getAccessToken())
                // apiKey
                .apiKeys(apiKeys)
                // 访问地址
                .apiBaseUrl(chatConfigDO.getApiBaseUrl())
                // 模型
                .apiModel(chatConfigDO.getGptModel().getName())
                // 反向代理地址，AccessToken 时用到
                .apiReverseProxy(chatConfigDO.getApiReverseProxy())
                .httpProxyHost(chatConfigDO.getHttpProxyHost())
                .httpProxyPort(chatConfigDO.getHttpProxyPort())
                .ipMaxRequest(chatConfigDO.getIpMaxRequest())
                .ipMaxRequestSecond(chatConfigDO.getIpMaxRequestSecond())
                .maxRequest(chatConfigDO.getMaxRequest())
                .maxRequestSecond(chatConfigDO.getMaxRequestSecond())
                .timeoutMs(chatConfigDO.getTimeoutMs())
                .maxContextSize(chatConfigDO.getMaxContextSize())
                .build();
    }

}

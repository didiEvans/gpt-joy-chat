package cloud.cstream.chat.client.api.apikey;

import cloud.cstream.chat.client.api.interceptor.DynamicApiKeyInterceptor;
import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.common.util.OkHttpClientUtil;
import cloud.cstream.chat.core.config.ChatConfig;
import cn.hutool.extra.spring.SpringUtil;
import com.unfbx.chatgpt.OpenAiStreamClient;
import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author Anker
 * @date 2023/3/24 16:09
 * ApiKey 聊天 Client 构建者
 */
@UtilityClass
public class ApiKeyChatClientBuilder {

    /**
     * 构建 API 流式请求客户端
     *
     * @return OpenAiStreamClient
     */
    public OpenAiStreamClient buildOpenAiStreamClient() {
        ChatConfig chatConfig = SpringUtil.getBean(ChatConfig.class);

        OkHttpClient okHttpClient = OkHttpClientUtil.getInstance(ApiTypeEnum.API_KEY, chatConfig.getTimeoutMs(),
                chatConfig.getTimeoutMs(), chatConfig.getTimeoutMs(), getProxy());

        return OpenAiStreamClient.builder()
                .okHttpClient(okHttpClient)
                .apiKey(chatConfig.getApiKeys())
                .apiHost(chatConfig.getApiBaseUrl())
                .authInterceptor(new DynamicApiKeyInterceptor(chatConfig.getWarringEmail()))
                .build();
    }

    /**
     * 获取 Proxy
     *
     * @return Proxy
     */
    private Proxy getProxy() {
        ChatConfig chatConfig = SpringUtil.getBean(ChatConfig.class);
        // 国内需要代理
        Proxy proxy = Proxy.NO_PROXY;
        if (chatConfig.hasHttpProxy()) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(chatConfig.getHttpProxyHost(), chatConfig.getHttpProxyPort()));
        }
        return proxy;
    }
}

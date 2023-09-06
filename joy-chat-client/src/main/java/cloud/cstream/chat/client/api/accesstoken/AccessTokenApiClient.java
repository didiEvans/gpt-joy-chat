package cloud.cstream.chat.client.api.accesstoken;

import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.common.util.ObjectMapperUtil;
import cloud.cstream.chat.common.util.OkHttpClientUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import lombok.Builder;
import okhttp3.*;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

/**
 * @author Anker
 * @date 2023/3/25 00:32
 * AccessTokenApiClient
 */
@Builder
public class AccessTokenApiClient {

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 反向代理地址
     */
    private String reverseProxy;

    /**
     * 模型
     */
    private String model;

    /**
     * 问答接口 stream 形式
     *
     * @param conversationRequest 对话请求参数
     * @param eventSourceListener sse 监听器
     */
    public void streamChatCompletions(ConversationRequest conversationRequest, EventSourceListener eventSourceListener) {
        // 构建请求头
        Headers headers = new Headers.Builder()
                .add(Header.AUTHORIZATION.name(), "Bearer ".concat(accessToken))
                .add(Header.ACCEPT.name(), "text/event-stream")
                .add(Header.CONTENT_TYPE.name(), ContentType.JSON.getValue())
                .build();

        // 构建 Request
        Request request = new Request.Builder()
                .url(reverseProxy)
                .post(RequestBody.create(ObjectMapperUtil.toJson(conversationRequest), MediaType.parse(ContentType.JSON.getValue())))
                .headers(headers)
                .build();
        // 创建事件
        OkHttpClient okHttpClient = OkHttpClientUtil.getInstance(ApiTypeEnum.ACCESS_TOKEN, 60000, 60000, 60000, null);
        EventSources.createFactory(okHttpClient).newEventSource(request, eventSourceListener);
    }
}


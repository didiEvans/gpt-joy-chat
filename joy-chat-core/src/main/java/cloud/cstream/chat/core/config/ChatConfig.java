package cloud.cstream.chat.core.config;

import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.common.enums.ConversationModelEnum;
import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;
import cloud.cstream.chat.core.ob.server.OpenAiConfigPropertiesObserver;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Objects;

/**
 * @author Anker
 * @date 2023/3/22 20:36
 * 聊天配置参数
 */
@Data
@Builder
@Slf4j
public class ChatConfig implements InitializingBean, OpenAiConfigPropertiesObserver {

    /**
     * OpenAI API Key
     * @link <a href="https://beta.openai.com/docs/api-reference/authentication"/>
     */
    private List<String> apiKeys;

    /**
     * Access Token
     * @link <a href="https://chat.openai.com/api/auth/session"/>
     */
    private String accessToken;

    /**
     * OpenAI API Base URL
     * @link <a href="https://api.openai.com"/>
     */
    private String apiBaseUrl;

    /**
     * OpenAI API Model
     * @link <a href="https://beta.openai.com/docs/models"/>
     */
    private String apiModel;

    /**
     * 反向代理地址，AccessToken 时用到
     */
    private String apiReverseProxy;

    /**
     * 超时毫秒
     */
    private Integer timeoutMs;

    /**
     * HTTP 代理主机
     */
    private String httpProxyHost;

    /**
     * HTTP 代理端口
     */
    private Integer httpProxyPort;

    /**
     * 全局时间内最大请求次数，默认无限制
     */
    private Integer maxRequest;

    /**
     * 全局最大请求时间间隔（秒）
     */
    private Integer maxRequestSecond;

    /**
     * ip 时间内最大请求次数，默认无限制
     */
    private Integer ipMaxRequest;

    /**
     * ip 最大请求时间间隔（秒）
     */
    private Integer ipMaxRequestSecond;

    /**
     * apiKey 失效后通知的邮箱地址
     */
    private List<String> warringEmail;

    /**
     * 最大上下文数量限制, 防止会话过长导致的性能问题
     */
    private Integer maxContextSize;

    /**
     * 判断是否有 http 代理
     *
     * @return true/false
     */
    public Boolean hasHttpProxy() {
        return StrUtil.isNotBlank(httpProxyHost) && Objects.nonNull(httpProxyPort);
    }

    /**
     * 获取 API 类型枚举
     *
     * @return API 类型枚举
     */
    public ApiTypeEnum getApiTypeEnum() {
        // 优先 API KEY
        if (CollUtil.isNotEmpty(apiKeys)) {
            return ApiTypeEnum.API_KEY;
        }
        return ApiTypeEnum.ACCESS_TOKEN;
    }

    /**
     * 获取全局时间内最大请求次数
     *
     * @return 最大请求次数
     */
    public Integer getMaxRequest() {
        return Opt.ofNullable(maxRequest).orElse(0);
    }

    /**
     * 获取全局最大请求时间间隔（秒）
     *
     * @return 时间间隔
     */
    public Integer getMaxRequestSecond() {
        return Opt.ofNullable(maxRequestSecond).orElse(0);
    }

    /**
     * 获取 ip 时间内最大请求次数
     *
     * @return 最大请求次数
     */
    public Integer getIpMaxRequest() {
        return Opt.ofNullable(ipMaxRequest).orElse(0);
    }

    /**
     * 获取 ip 最大请求时间间隔（秒）
     *
     * @return 时间间隔
     */
    public Integer getIpMaxRequestSecond() {
        return Opt.ofNullable(ipMaxRequestSecond).orElse(0);
    }


    public Integer getMaxContextSize(){
        return Opt.ofNullable(maxContextSize).orElse(0);
    }


    @Override
    public void afterPropertiesSet() {
        if (CollUtil.isEmpty(apiKeys) && StrUtil.isBlank(accessToken)) {
            throw new RuntimeException("apiKey 或 accessToken 必须有值");
        }

        // ApiKey
        if (getApiTypeEnum() == ApiTypeEnum.API_KEY) {
            // apiBaseUrl 必须有值
            if (StrUtil.isBlank(apiBaseUrl)) {
                throw new RuntimeException("openaiApiBaseUrl 必须有值");
            }

            // 校验 apiModel
            if (StrUtil.isBlank(apiModel)) {
                apiModel = ChatCompletion.Model.GPT_3_5_TURBO.getName();
                return;
            }
            ChatCompletion.Model[] models = ChatCompletion.Model.values();
            for (ChatCompletion.Model model : models) {
                if (model.getName().equals(apiModel)) {
                    return;
                }
            }
            throw new RuntimeException("ApiKey apiModel 填写错误");
        }

        // AccessToken
        if (getApiTypeEnum() == ApiTypeEnum.ACCESS_TOKEN) {
            // apiReverseProxy 必须有值
            if (StrUtil.isBlank(apiReverseProxy)) {
                throw new RuntimeException("apiReverseProxy 必须有值");
            }

            // 校验 apiModel
            if (StrUtil.isBlank(apiModel)) {
                apiModel = ConversationModelEnum.DEFAULT_GPT_3_5.getName();
                return;
            }

            if (!ConversationModelEnum.NAME_MAP.containsKey(apiModel)) {
                throw new RuntimeException("AccessToken apiModel 填写错误");
            }
        }
        if (Objects.isNull(getMaxContextSize()) || CoreConstants.MIN_CONTEXT_SIZE < getMaxContextSize()){
            throw new RuntimeException("max context size ");
        }

        if (CollUtil.isEmpty(warringEmail)){
            setWarringEmail(CoreConstants.DEFAULT_WARRING_EMAIL);
        }
    }

    @Override
    public void update(OpenaiChatConfigDO openAiConfig) {
        log.info("will refresh on time:<{}> -- update after config is :{}", DatePattern.NORM_DATETIME_FORMAT.format(DateUtil.date()), JSONUtil.toJsonPrettyStr(openAiConfig));
        if (CharSequenceUtil.isNotBlank(openAiConfig.getApiKeys()) && JSONUtil.isTypeJSONArray(openAiConfig.getApiKeys())){
            this.setApiKeys(JSONUtil.toList(openAiConfig.getApiKeys(), String.class));
        }
        if (CharSequenceUtil.isNotBlank(openAiConfig.getApiBaseUrl())){
            this.setApiBaseUrl(openAiConfig.getApiBaseUrl());
        }
        if (CharSequenceUtil.isNotBlank(openAiConfig.getWarringEmail()) && JSONUtil.isTypeJSONArray(openAiConfig.getWarringEmail())){
            this.setWarringEmail(JSONUtil.toList(openAiConfig.getWarringEmail(), String.class));
        }
        if (Objects.nonNull(openAiConfig.getGptModel())){
            this.setApiModel(openAiConfig.getGptModel().getName());
        }
        this.setIpMaxRequest(openAiConfig.getIpMaxRequest());
        this.setIpMaxRequestSecond(openAiConfig.getIpMaxRequestSecond());
        this.setMaxContextSize(openAiConfig.getMaxContextSize());
        this.setTimeoutMs(openAiConfig.getTimeoutMs());
        this.setMaxRequest(openAiConfig.getMaxRequest());
        this.setMaxRequestSecond(openAiConfig.getMaxRequestSecond());
        log.info("refresh completed on time:<{}> , current chat config is :{}", DatePattern.NORM_DATETIME_FORMAT.format(DateUtil.date()), JSONUtil.toJsonPrettyStr(this));
    }
}

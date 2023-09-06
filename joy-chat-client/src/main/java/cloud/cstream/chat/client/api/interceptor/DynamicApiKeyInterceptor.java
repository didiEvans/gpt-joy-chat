package cloud.cstream.chat.client.api.interceptor;

import cloud.cstream.chat.common.enums.ApiKeyStatusEnum;
import cloud.cstream.chat.core.service.EmailService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.unfbx.chatgpt.entity.common.OpenAiResponse;
import com.unfbx.chatgpt.exception.BaseException;
import com.unfbx.chatgpt.exception.CommonError;
import com.unfbx.chatgpt.interceptor.OpenAiAuthInterceptor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class DynamicApiKeyInterceptor extends OpenAiAuthInterceptor {


    private final List<String> warringEmails;

    /**
     * 构造方法
     *
     * @param warringEmails 告警邮件发送目标
     */
    public DynamicApiKeyInterceptor(List<String> warringEmails) {
        this.warringEmails = warringEmails;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String key = getKey();
        Request original = chain.request();
        Request request = this.auth(key, original);
        Response response = chain.proceed(request);
        if (!response.isSuccessful()) {
            String errorMsg = response.body().string();
            if (response.code() == CommonError.OPENAI_AUTHENTICATION_ERROR.code()
                    || response.code() == CommonError.OPENAI_LIMIT_ERROR.code()
                    || response.code() == CommonError.OPENAI_SERVER_ERROR.code()) {
                OpenAiResponse<?> openAiResponse = JSONUtil.toBean(errorMsg, OpenAiResponse.class);
                String errorCode = openAiResponse.getError().getCode();
                log.error("--------> 请求openai异常，错误code：{}", errorCode);
                log.error("--------> 请求异常：{}", errorMsg);
                //账号被封或者key不正确就移除掉
                if (ApiKeyStatusEnum.apiKeyIsException(errorCode)) {
                    super.setApiKey(this.onErrorDealApiKeys(key));
                }
                throw new BaseException(openAiResponse.getError().getMessage());
            }
            //非官方定义的错误code
            log.error("--------> 请求异常：{}", errorMsg);
            OpenAiResponse<?> openAiResponse = JSONUtil.toBean(errorMsg, OpenAiResponse.class);
            if (Objects.nonNull(openAiResponse.getError())) {
                log.error(openAiResponse.getError().getMessage());
                throw new BaseException(openAiResponse.getError().getMessage());
            }
            throw new BaseException(CommonError.RETRY_ERROR);
        }
        return response;
    }


    @Override
    protected List<String> onErrorDealApiKeys(String errorKey) {
        List<String> apiKey = super.getApiKey().stream().filter(e -> !errorKey.equals(e)).collect(Collectors.toList());
        String message = StrUtil.format("ApiKey：<{}> 失效了，即将移除！", errorKey);
        if (CollUtil.isEmpty(apiKey)){
            message = message.concat("移除后,无可用apiKey,请注意");
        }
        log.error("--------> 当前ApiKey：[{}] 失效了，移除！", errorKey);
        this.sendEmailWarring(message);
        return apiKey;
    }

    /**
     * 所有的key都失效后，自定义预警配置
     * 不配置直接return
     */
    @Override
    protected void noHaveActiveKeyWarring() {
        log.error("--------> [告警] 没有可用的key！！！");
        this.sendEmailWarring("[告警] 没有可用的key！！！");
    }

    @Override
    public Request auth(String key, Request original) {
        return super.auth(key, original);
    }


    private void sendEmailWarring(String content){
        EmailService emailService = SpringUtil.getBean(EmailService.class);
        emailService.send(this.warringEmails, content);
    }
}

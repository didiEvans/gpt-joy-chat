package cloud.cstream.chat.core.domain.request;

import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Data
public class OpenaiChatConfigRequest {

    @NotNull(message = "请指定 id")
    private Integer id;
    /**
     * OpenAI API Key
     */
    @NotNull(message = "apiKey为关键参数,不能为空")
    private String apiKeys;
    /**
     * OpenAI API Base URL
     */
    @NotNull(message = "访问基础地址为关键参数,不能为空")
    private String apiBaseUrl;

    /**
     * OpenAI API Model
     */
    @NotNull(message = "模型为关键参数,不能为空")
    private ChatCompletion.Model gptModel;
    /**
     * 超时毫秒
     */
    private Integer timeoutMs;
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
     * 最大上下文数量限制, 防止会话过长导致的性能问题, 也不能太少, 最小10
     */
    @NotNull(message = "最大上下文长度为关键参数,不能为空")
    @Min(value = 10, message = "最小长度不能低于10")
    private Integer maxContextSize;
    /**
     * 告警邮箱
     * JSONArray 格式 ["asda@cc.com","bbb@cc.com"]
     */
    @NotNull(message = "告警邮箱不能为空,不能为空")
    private String warringEmail;
}

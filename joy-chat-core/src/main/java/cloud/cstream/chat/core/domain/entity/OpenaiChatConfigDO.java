package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import lombok.Data;

/**
 * @author Anker
 * @date 2023/3/22 20:36
 * 聊天配置参数
 */
@Data
@TableName("openai_chat_config")
public class OpenaiChatConfigDO{

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * OpenAI API Key
     * @link <a href="https://beta.openai.com/docs/api-reference/authentication"/>
     */
    private String apiKeys;

    /**
     * Access Token
     * @link <a href="https://chat.openai.com/api/auth/session"/> sys_prompt_lib
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
    private ChatCompletion.Model gptModel;

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
     * 最大上下文数量限制, 防止会话过长导致的性能问题, 也不能太少, 最小10
     */
    private Integer maxContextSize;
    /**
     * 告警邮箱
     * JSONArray 格式 ["asda@cc.com","bbb@cc.com"]
     */
    private String warringEmail;
}

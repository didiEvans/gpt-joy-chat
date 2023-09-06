package cloud.cstream.chat.client.domain.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 消息处理请求
 *
 * @author Anker
 * @date 2023/3/23 13:17
 * 消息处理请求
 */
@Data
public class ChatProcessRequest {
    /**
     * 问题
     */
    @Size(min = 1, max = 2000, message = "问题字数范围[1, 2000]")
    private String prompt;
    /**
     * 配置
     */
    private Options options;
    /**
     * 系统消息
     */
    private String systemMessage;
    /**
     * 系统提示词id
     */
    private String sysPromptLibId;
    /**
     * 前置训练模型
     */
    private Integer trainingModelId;
    /**
     * 是否审核系统提示词消息
     */
    private boolean reviewSysPrompt;

    /**
     * 消息配置
     */
    @Data
    public static class Options {
        /**
         * 对话 id
         */
        private String conversationId;

        /**
         * 这里的父级消息指的是回答的父级消息 id
         * 前端发送问题，需要上下文的话传回答的父级消息 id
         */
        private String parentMessageId;
    }
}

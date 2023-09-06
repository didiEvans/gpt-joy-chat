package cloud.cstream.chat.core.domain.vo;

import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.common.enums.ChatMessageStatusEnum;
import cloud.cstream.chat.common.enums.ChatMessageTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/3/28 12:01
 * 聊天记录展示参数
 */
@Data
public class ChatMessageVO {
    /**
     * 消息 id
     */
    private String messageId;
    /**
     * 父级消息 id, 第一条消息父级消息 id 为空；回答的父级消息 id 不能为空
     */
    private String parentMessageId;

    /**
     * 父级回答消息 id, 当前消息是问题：则 parentMessageId=parentAnswerMessageId
     */
    private String parentAnswerMessageId;

    /**
     * 父级问题消息 id, 当前消息是回答：则 parentMessageId=parentQuestionMessageId
     */
    private String parentQuestionMessageId;

    /**
     * 上下文数量, 包含了问题和回答的数量；第一条消息是 1
     */
    private Integer contextCount;

    /**
     *  问题上下文数量, 包含了连续的问题的数量；第一条消息是 1
     */
    private Integer questionContextCount;

    /**
     * 消息类型枚举, 第一条消息一定是问题
     */
    private ChatMessageTypeEnum messageType;

    /**
     *  聊天室 id
     */
    private Long chatRoomId;

    /**
     *   对话 id
     */
    private String conversationId;

    /**
     *  API 类型
     */
    private ApiTypeEnum apiType;

    /**
     *  ip
     */
    private String ip;

    /**
     *  消息内容, 包含上下文的对话这里只会显示出用户发送的
     */
    private String content;

    /**
     * 错误响应数据
     */
    private String responseErrorData;

    /**
     * 输入消息的 tokens
     */
    private Long promptTokens;

    /**
     *  输出消息的 tokens
     */
    private Long completionTokens;

    /**
     *  累计 Tokens
     */
    private Long totalTokens;

    /**
     * 聊天信息状态
     */
    private ChatMessageStatusEnum status;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 发送用户
     */
    private String sendUserName;
}

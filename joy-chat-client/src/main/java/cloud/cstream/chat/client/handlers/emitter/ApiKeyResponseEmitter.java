package cloud.cstream.chat.client.handlers.emitter;

import cloud.cstream.chat.client.api.apikey.ApiKeyChatClientBuilder;
import cloud.cstream.chat.client.api.listener.ParsedEventSourceListener;
import cloud.cstream.chat.client.api.listener.ResponseBodyEmitterStreamListener;
import cloud.cstream.chat.client.api.parser.ChatCompletionResponseParser;
import cloud.cstream.chat.client.api.storage.ApiKeyDatabaseDataStorage;
import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.client.service.ChatMessageService;
import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.enums.ApiKeyTokenLimiterEnum;
import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.common.enums.ChatMessageStatusEnum;
import cloud.cstream.chat.common.enums.ChatMessageTypeEnum;
import cloud.cstream.chat.common.util.ObjectMapperUtil;
import cloud.cstream.chat.core.config.ChatConfig;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.utils.TikTokensUtil;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Anker
 * @date 2023/3/24 15:51
 * ApiKey 响应处理
 */
@Component
public class ApiKeyResponseEmitter implements ResponseEmitter {

    @Resource
    private ChatConfig chatConfig;
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private ChatCompletionResponseParser parser;
    @Resource
    private ApiKeyDatabaseDataStorage dataStorage;

    @Override
    public ResponseBodyEmitter requestToResponseEmitter(ChatProcessRequest chatProcessRequest, ResponseBodyEmitter emitter) {
        // 初始化聊天消息
        ChatMessageDO chatMessageDO = chatMessageService.initChatMessage(chatProcessRequest, ApiTypeEnum.API_KEY);
        // 构建消息联表
        LinkedList<Message> messages = buildMessageLink(chatMessageDO, chatProcessRequest);
        // 动态优化消息上下文, 使其不被上下文限制打断对话
        int totalMsgTokens = this.dynamicOptimizationContext(chatMessageDO.getModelName(), messages);
        // 构建聊天参数
        ChatCompletion chatCompletion = ChatCompletion.builder()
                // 最大的 tokens = 模型的最大上线 - 本次 prompt 消耗的 tokens
                .maxTokens(ApiKeyTokenLimiterEnum.getTokenLimitByOuterJarModelName(chatConfig.getApiModel()) - totalMsgTokens - 1)
                // api 模型
                .model(chatConfig.getApiModel())
                // [0, 2] 越低越精准
                .temperature(0.8).topP(1.0)
                // 每次生成一条
                .n(1).presencePenalty(1)
                // 消息
                .messages(messages)
                // 流式输出
                .stream(true)
                .build();

        // 构建事件监听器
        ParsedEventSourceListener parsedEventSourceListener = new ParsedEventSourceListener.Builder()
                .addListener(new ResponseBodyEmitterStreamListener(emitter))
                .setParser(parser)
                .setDataStorage(dataStorage)
                .setOriginalRequestData(ObjectMapperUtil.toJson(chatCompletion))
                .setChatMessageDO(chatMessageDO)
                .build();

        ApiKeyChatClientBuilder.buildOpenAiStreamClient().streamChatCompletion(chatCompletion, parsedEventSourceListener);
        return emitter;
    }

    /**
     * 构建上下文消息联表
     *
     * @param chatMessageDO      消息对象实体
     * @param chatProcessRequest 消息请求
     * @return {@link LinkedList<Message> } 消息联表
     */
    @NotNull
    private LinkedList<Message> buildMessageLink(ChatMessageDO chatMessageDO, ChatProcessRequest chatProcessRequest) {
        // 所有消息
        LinkedList<Message> messages = Lists.newLinkedList();
        // 添加用户上下文消息
        addContextChatMessage(chatMessageDO, messages, chatConfig.getMaxContextSize());
        // 系统角色消息
        if (StrUtil.isNotBlank(chatProcessRequest.getSystemMessage())) {
            // 系统消息
            Message systemMessage = Message.builder().role(Message.Role.SYSTEM).content(chatProcessRequest.getSystemMessage()).build();
            messages.addFirst(systemMessage);
        }
        return messages;
    }

    /**
     * 检查上下文消息的 Token 数是否超出模型限制
     *
     * @param modelName 当前使用的模型名称
     * @param messages  参与计算的消息上下文
     */
    private Integer dynamicOptimizationContext(String modelName, LinkedList<Message> messages) {
        // 计算当前消息token总量
        int totalTokenCount = TikTokensUtil.tokens(modelName, messages);
        if (!ApiKeyTokenLimiterEnum.exceedsLimit(modelName, (totalTokenCount + CoreConstants.TOKEN_COOL_VALUE))) {
            return totalTokenCount;
        }
        // 如果超出token限制, 将最老的会话丢弃
        messages.removeLast();
        // 重新计算
        return this.dynamicOptimizationContext(modelName, messages);
    }

    /**
     * 添加上下文问题消息
     *
     * @param chatMessageDO 当前消息
     * @param messages      消息列表
     */
    private void addContextChatMessage(ChatMessageDO chatMessageDO, LinkedList<Message> messages, Integer maxContextSize) {
        if (Objects.isNull(chatMessageDO)) {
            return;
        }
        // 父级消息id为空，表示是第一条消息，直接添加到message里
        if (Objects.isNull(chatMessageDO.getParentMessageId())) {
            messages.addFirst(Message.builder().role(Message.Role.USER).content(chatMessageDO.getContent()).build());
            return;
        }
        // 到达最大上下文数量限制, 停止递归查找
        if (NumConstant.INT_ZERO.equals(maxContextSize)) {
            return;
        }
        // 回答不成功的情况下，不添加回答消息记录和该回答的问题消息记录
        if (chatMessageDO.getMessageType() == ChatMessageTypeEnum.ANSWER && chatMessageDO.getStatus() != ChatMessageStatusEnum.PART_SUCCESS && chatMessageDO.getStatus() != ChatMessageStatusEnum.COMPLETE_SUCCESS) {
            // 没有父级回答消息直接跳过
            if (Objects.isNull(chatMessageDO.getParentAnswerMessageId())) {
                return;
            }
            ChatMessageDO parentMessage = chatMessageService.getOne(new LambdaQueryWrapper<ChatMessageDO>().eq(ChatMessageDO::getMessageId, chatMessageDO.getParentAnswerMessageId()));
            addContextChatMessage(parentMessage, messages, maxContextSize);
            return;
        }
        // 根据消息类型去选择角色，需要添加问题和回答到上下文
        Message.Role role = (chatMessageDO.getMessageType() == ChatMessageTypeEnum.ANSWER) ? Message.Role.ASSISTANT : Message.Role.USER;
        // 从下往上找并添加，越上面的数据放越前面
        messages.addFirst(Message.builder().role(role).content(chatMessageDO.getContent()).build());
        maxContextSize -= 1;
        ChatMessageDO parentMessage = chatMessageService.getOne(new LambdaQueryWrapper<ChatMessageDO>().eq(ChatMessageDO::getMessageId, chatMessageDO.getParentMessageId()));
        addContextChatMessage(parentMessage, messages, maxContextSize);
    }
}

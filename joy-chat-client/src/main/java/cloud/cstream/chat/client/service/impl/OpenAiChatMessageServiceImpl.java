package cloud.cstream.chat.client.service.impl;


import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.client.handlers.emitter.ChatProcessFilterChain;
import cloud.cstream.chat.client.service.ChatMessageService;
import cloud.cstream.chat.client.service.ChatRoomService;
import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.enums.ApiTypeEnum;
import cloud.cstream.chat.common.enums.ChatMessageStatusEnum;
import cloud.cstream.chat.common.enums.ChatMessageTypeEnum;
import cloud.cstream.chat.common.enums.InviteTaskStageEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.core.config.ChatConfig;
import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import cloud.cstream.chat.core.domain.entity.ChatRoomDO;
import cloud.cstream.chat.core.mapper.ChatMessageMapper;
import cloud.cstream.chat.core.service.FrontUserInviteTaskRecordService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * tips
 *
 * @author YBin
 * @date 2023/4/25 19:28
 */
@Slf4j
@Service
public class OpenAiChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessageDO> implements ChatMessageService {


    @Autowired
    private ChatConfig chatConfig;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatProcessFilterChain chatProcessFilterChain;
    @Autowired
    private FrontUserInviteTaskRecordService frontUserInviteTaskRecordService;


    @Override
    public ResponseBodyEmitter sendMessage(ChatProcessRequest chatProcessRequest) {
        // 超时时间设置 3 分钟
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(3 * 60 * 1000L);
        chatProcessFilterChain.doChain(chatProcessRequest, emitter);
        // 尝试完成对话邀请任务
        frontUserInviteTaskRecordService.tryCompleteTaskStage(InviteTaskStageEnum.SESSION_SUCCESSFUL, ClientUserLoginUtil.getUserId());
        return emitter;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ChatMessageDO initChatMessage(ChatProcessRequest chatProcessRequest, ApiTypeEnum apiTypeEnum) {
        ChatMessageDO chatMessageDO = new ChatMessageDO();
        chatMessageDO.setId(IdWorker.getId());
        // 消息 id 手动生成
        chatMessageDO.setMessageId(UUID.randomUUID().toString());
        chatMessageDO.setMessageType(ChatMessageTypeEnum.QUESTION);
        chatMessageDO.setApiType(apiTypeEnum);
        if (apiTypeEnum == ApiTypeEnum.API_KEY) {
            chatMessageDO.setApiKey(chatConfig.getApiKeys().get(0));
        }
        chatMessageDO.setUserId(ClientUserLoginUtil.getUserId());
        chatMessageDO.setContent(chatProcessRequest.getPrompt());
        chatMessageDO.setModelName(chatConfig.getApiModel());
        chatMessageDO.setOriginalData(null);
        chatMessageDO.setPromptTokens(-1);
        chatMessageDO.setCompletionTokens(-1);
        chatMessageDO.setTotalTokens(-1);
        chatMessageDO.setIp(WebUtil.getIp());
        chatMessageDO.setStatus(ChatMessageStatusEnum.INIT);
        chatMessageDO.setCreateTime(new Date());
        chatMessageDO.setUpdateTime(new Date());

        // 填充初始化父级消息参数
        populateInitParentMessage(chatMessageDO, chatProcessRequest);

        save(chatMessageDO);
        return chatMessageDO;
    }

    /**
     * 填充初始化父级消息参数
     *
     * @param chatMessageDO      消息记录
     * @param chatProcessRequest 消息处理请求参数
     */
    private void populateInitParentMessage(ChatMessageDO chatMessageDO, ChatProcessRequest chatProcessRequest) {
        // 父级消息 id
        String parentMessageId = Optional.ofNullable(chatProcessRequest.getOptions())
                .map(ChatProcessRequest.Options::getParentMessageId)
                .orElse(null);

        // 对话 id
        String conversationId = Optional.ofNullable(chatProcessRequest.getOptions())
                .map(ChatProcessRequest.Options::getConversationId)
                .orElse(null);

        if (StrUtil.isAllNotBlank(parentMessageId, conversationId)) {
            // 寻找父级消息
            ChatMessageDO parentChatMessage = getOne(new LambdaQueryWrapper<ChatMessageDO>()
                    // 用户 id 一致
                    .eq(ChatMessageDO::getUserId, ClientUserLoginUtil.getUserId())
                    // 消息 id 一致
                    .eq(ChatMessageDO::getMessageId, parentMessageId)
                    // 对话 id 一致
                    .eq(ChatMessageDO::getConversationId, conversationId)
                    // Api 类型一致
                    .eq(ChatMessageDO::getApiType, chatMessageDO.getApiType())
                    // 消息类型为回答
                    .eq(ChatMessageDO::getMessageType, ChatMessageTypeEnum.ANSWER));
            if (Objects.isNull(parentChatMessage)) {
                throw new ServiceException("父级消息不存在，本次对话出错，请先关闭上下文或开启新的对话窗口");
            }

            chatMessageDO.setParentMessageId(parentMessageId);
            chatMessageDO.setParentAnswerMessageId(parentMessageId);
            chatMessageDO.setParentQuestionMessageId(parentChatMessage.getParentQuestionMessageId());
            chatMessageDO.setChatRoomId(parentChatMessage.getChatRoomId());
            chatMessageDO.setConversationId(parentChatMessage.getConversationId());
            chatMessageDO.setContextCount(parentChatMessage.getContextCount() + 1);
            chatMessageDO.setQuestionContextCount(parentChatMessage.getQuestionContextCount() + 1);

            if (chatMessageDO.getApiType() == ApiTypeEnum.ACCESS_TOKEN) {
                if (!Objects.equals(chatMessageDO.getModelName(), parentChatMessage.getModelName())) {
                    throw new ServiceException("当前对话类型为 AccessToken 使用模型不一样，请开启新的对话");
                }
            }

            // ApiKey 限制上下文问题的数量
//            if (chatMessageDO.getApiType() == ApiTypeEnum.API_KEY
//                    && chatConfig.getLimitQuestionContextCount() > 0
//                    && chatMessageDO.getQuestionContextCount() > chatConfig.getLimitQuestionContextCount()) {
//                throw new ServiceException(StrUtil.format("当前允许连续对话的问题数量为[{}]次，已达到上限，请关闭上下文对话重新发送", chatConfig.getLimitQuestionContextCount()));
//            }
        } else {
            // 创建新聊天室
            ChatRoomDO chatRoomDO = chatRoomService.createChatRoom(chatMessageDO);
            chatMessageDO.setChatRoomId(chatRoomDO.getId());
            chatMessageDO.setContextCount(1);
            chatMessageDO.setQuestionContextCount(1);
        }
    }
}

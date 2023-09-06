package cloud.cstream.chat.client.handlers.emitter;

import cloud.cstream.chat.client.domain.request.ChatProcessRequest;
import cloud.cstream.chat.client.domain.vo.ChatReplyMessageVO;
import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.util.ObjectMapperUtil;
import cloud.cstream.chat.core.domain.entity.SensitiveWordTriggerRecord;
import cloud.cstream.chat.core.handler.SensitiveWordHandler;
import cloud.cstream.chat.core.service.SensitiveWordTriggerRecordService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.List;

/**
 * @author Anker
 * @date 2023/3/29 11:58
 * 敏感词检测
 */
public class SensitiveWordEmitterChain extends AbstractResponseEmitterChain {

    @Override
    public void doChain(ChatProcessRequest request, ResponseBodyEmitter emitter) {
        List<String> prompts = SensitiveWordHandler.checkWord(request.getPrompt(), false);
        List<String> systemMessages = Lists.newArrayList();
        if (request.isReviewSysPrompt()){
            systemMessages = SensitiveWordHandler.checkWord(request.getSystemMessage(), true);
        }
        try {
            if (CollUtil.isNotEmpty(prompts) || CollUtil.isNotEmpty(systemMessages)){
                List<String> words = Lists.newArrayList();
                words.addAll(prompts);
                words.addAll(systemMessages);
                // 如果其中任意一个命中敏感词
                SensitiveWordTriggerRecord build = SensitiveWordTriggerRecord.builder()
                        .conversationId(request.getOptions().getConversationId())
                        .word(CollUtil.join(words, StrPool.COMMA))
                        .content(request.getPrompt())
                        .sysContent(request.getSystemMessage())
                        .userId(ClientUserLoginUtil.getUserId())
                        .build();
                SensitiveWordTriggerRecordService bean = SpringUtil.getBean(SensitiveWordTriggerRecordService.class);
                bean.save(build);
                bean.hitSensitiveWordLimit(ClientUserLoginUtil.getUserId(), true);
            }
            // 取上一条消息的 parentMessageId 和 conversationId，使得敏感词检测未通过时不影响上下文
            ChatReplyMessageVO chatReplyMessageVO = ChatReplyMessageVO.onEmitterChainException(request);
            if (CollectionUtil.isNotEmpty(prompts)) {
                chatReplyMessageVO.setText(StrUtil.format("发送失败，包含敏感词{}", prompts));
                emitter.send(ObjectMapperUtil.toJson(chatReplyMessageVO));
                emitter.complete();
                return;
            }

            if (CollectionUtil.isNotEmpty(systemMessages)) {
                chatReplyMessageVO.setText(StrUtil.format("发送失败，系统消息包含敏感词{}", prompts));
                emitter.send(ObjectMapperUtil.toJson(chatReplyMessageVO));
                emitter.complete();
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (getNext() != null) {
            getNext().doChain(request, emitter);
        }
    }
}

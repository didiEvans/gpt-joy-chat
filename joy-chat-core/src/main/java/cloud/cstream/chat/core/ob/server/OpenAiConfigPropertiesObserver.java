package cloud.cstream.chat.core.ob.server;

import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;

/**
 * chatConfig属性变更观察者
 *
 * @author Anker
 */
public interface OpenAiConfigPropertiesObserver {

    /**
     * 触发更新
     * @param openAiConfig chatGPT配置
     */
    void update(OpenaiChatConfigDO openAiConfig);

}

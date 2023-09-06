package cloud.cstream.chat.core.ob.servable;

import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;
import cloud.cstream.chat.core.ob.server.OpenAiConfigPropertiesObserver;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 被观察者
 *
 * @author Anker
 */
@Slf4j
@Component
public class OpenAiConfigPropertiesObserved {

    @Autowired
    private OpenAiConfigPropertiesObserver observer;

    /**
     * 配置刷新
     */
    public void refreshConf(OpenaiChatConfigDO chatConfigDO){
        log.info("trigger the chat config update event, will refresh on time:<{}>", DatePattern.NORM_DATETIME_FORMAT.format(DateUtil.date()));
        observer.update(chatConfigDO);
    }

}

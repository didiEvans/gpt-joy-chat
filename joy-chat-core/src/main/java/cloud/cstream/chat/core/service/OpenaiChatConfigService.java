package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;
import cloud.cstream.chat.core.domain.request.OpenaiChatConfigRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
public interface OpenaiChatConfigService extends IService<OpenaiChatConfigDO> {
    /**
     * 获取openAiConfig
     *
     * @return {@link OpenaiChatConfigDO}
     */
    OpenaiChatConfigDO getOpenAiConfig();

    /**
     * 配置更新
     * @param request
     */
    void updateConf(OpenaiChatConfigRequest request);

    /**
     * 查询chatGOT模型下拉选择器
     *
     * @return
     */
    List<ChatCompletion.Model> getModelSelector();

}

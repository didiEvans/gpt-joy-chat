package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;
import cloud.cstream.chat.core.domain.request.OpenaiChatConfigRequest;
import cloud.cstream.chat.core.service.OpenaiChatConfigService;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * admin-openAI ChatGPT配置
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */
@RestController
@RequestMapping("admin/openai_chat_config")
public class OpenaiChatConfigController {

    @Autowired
    private OpenaiChatConfigService openaiChatConfigService;

    /**
     * 查询配置详情
     *
     * @return
     */
    @GetMapping("detail")
    public R<OpenaiChatConfigDO> getConfigDetail(){
        return R.data(openaiChatConfigService.getOpenAiConfig());
    }

    /**
     * 更新chatGPT配置
     * @param request 请求参数
     */
    @PostMapping("update")
    public R<Void> updateConfig(@RequestBody OpenaiChatConfigRequest request){
        openaiChatConfigService.updateConf(request);
        return R.success();
    }

    /**
     * 模型选择下拉
     * @return 模型
     */
    @GetMapping("/model_selector")
    public R<List<ChatCompletion.Model>> getModelSelector(){
        return R.data(openaiChatConfigService.getModelSelector());
    }


}

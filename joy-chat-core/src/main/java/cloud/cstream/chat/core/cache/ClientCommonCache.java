package cloud.cstream.chat.core.cache;


import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.core.domain.response.YiJAiDrawResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端公共缓存
 *
 * @author Anker
 */
public interface ClientCommonCache {

    /**
     * 绘画任务响应结果缓存
     */
    Map<String, List<YiJAiDrawResponse>> YJ_DRAW_TASK_RESP_CACHE = new HashMap<>(CoreConstants.Draw_TASK_QUEUE * 4);


    /**
     * 绘画任务响应异常错误的结果缓存
     */
    Map<String, String> YJ_DRAW_TASK_RESP_ERROR_CACHE = new HashMap<>(CoreConstants.Draw_TASK_QUEUE * 4);


}

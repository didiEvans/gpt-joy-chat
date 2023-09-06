package cloud.cstream.chat.common.enums;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 意间ai会话Api地址枚举
 *
 * @author Anker
 */
@Getter
@AllArgsConstructor
public enum YiJAiDrawApiEnum {

    CREATE_DRAW_TASK("/painting-open-api/site/put_task", "开启一个绘画任务"),

    GET_TASK_PROCESS("/painting-open-api/site/show_task_detail", "获取指定绘画任务详情"),

    BATCH_TASK_PROCESS("/painting-open-api/site/show_task_detail_batch", "批量获取任务详情"),


    GET_COMPLETED_TASK_TABLE("/painting-open-api/site/show_complete_tasks", "获取已完成的任务列表"),

    CANCEL_TASK("/painting-open-api/site/show_complete_tasks", "取消绘画任务"),


    GET_DRAW_SELECTOR("/painting-open-api/site/get_draw_selector4", "获取话机风格")
    ;


    private final String api;

    private final String describe;


    public static String fullApiAddress(String host, YiJAiDrawApiEnum apiEnum){
        StringBuilder urlBuilder = StrUtil.builder(host);
        if (!host.endsWith(StrPool.SLASH)){
            urlBuilder.append(StrPool.SLASH);
        }
        return urlBuilder.append(apiEnum.getApi()).toString();
    }

}


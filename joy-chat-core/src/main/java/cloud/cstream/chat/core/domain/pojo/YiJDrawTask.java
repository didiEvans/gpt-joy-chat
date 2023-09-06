package cloud.cstream.chat.core.domain.pojo;

import cloud.cstream.chat.core.domain.request.draw.YiJCreateDrawRequest;
import lombok.Builder;
import lombok.Data;

/**
 * 意间绘画请求任务参数
 *
 * @author Anker
 */
@Data
@Builder
public class YiJDrawTask {
    /**
     * 任务发起用户id
     */
    private Integer userId;
    /**
     * 执行任务标识id
     */
    private String requestId;
    /**
     * 本次创建绘画任务请求参数
     */
    private YiJCreateDrawRequest request;


}

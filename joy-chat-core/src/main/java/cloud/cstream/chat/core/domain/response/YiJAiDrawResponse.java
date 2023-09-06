package cloud.cstream.chat.core.domain.response;

import lombok.Data;

import java.util.Date;

@Data
public class YiJAiDrawResponse {

    /**
     * 任务id
     */
    private String Uuid;
    /**
     * 任务创建时间
     */
    private Date CreateTime;
    /**
     * 队列名称
     */
    private String QueueName;

}

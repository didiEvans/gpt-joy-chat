package cloud.cstream.chat.core.domain.request.draw;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AiDrawBaseRequest {

    /**
     * 绘画处理类
     */
    @NotNull(message = "请指定绘画引擎")
    private Integer handlerCode;
    /**
     * apiKey
     */
    private String apikey;

    /**
     * 时间戳
     */
    private long timestamp;


    public long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}

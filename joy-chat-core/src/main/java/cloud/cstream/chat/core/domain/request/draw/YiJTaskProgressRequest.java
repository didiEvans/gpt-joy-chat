package cloud.cstream.chat.core.domain.request.draw;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class YiJTaskProgressRequest extends AiDrawBaseRequest {

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 任务uuid
     */
    private String uuid;
    /**
     * 查询任务uuid集合
     */
    @NotNull(message = "任务id不能为空")
    private List<String> uuids;

}

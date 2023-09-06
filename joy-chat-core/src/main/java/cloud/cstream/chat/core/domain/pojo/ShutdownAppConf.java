package cloud.cstream.chat.core.domain.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShutdownAppConf {

    /**
     * 开关 0 ：关闭 1：升级
     */
    @NotNull(message = "Please provide a valid port number")
    private Integer enableSwitch;

    /**
     * 公告内容
     */
    @NotNull(message = "Please provide a valid message")
    private String announce;
}

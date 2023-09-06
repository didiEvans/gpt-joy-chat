package cloud.cstream.chat.core.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneExistsRequest {

    /**
     * 标识
     */
    @NotBlank(message = "用户标识不能为空")
    private String identify;


}

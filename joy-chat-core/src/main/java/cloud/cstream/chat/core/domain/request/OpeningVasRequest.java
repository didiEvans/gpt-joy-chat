package cloud.cstream.chat.core.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/5/7
 */
@Data
public class OpeningVasRequest {

    @NotNull(message = "请选择要开通的增值套餐")
    private Integer vasPkgId;

    @NotNull(message = "用户id")
    private Integer uid;
}

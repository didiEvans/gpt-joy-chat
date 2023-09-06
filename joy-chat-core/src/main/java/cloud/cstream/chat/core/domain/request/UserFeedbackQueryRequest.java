package cloud.cstream.chat.core.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Data
public class UserFeedbackQueryRequest {

    /**
     * 反馈组id
     */
    @NotNull(message = "请选择反馈事项")
    private Integer groupId;




}




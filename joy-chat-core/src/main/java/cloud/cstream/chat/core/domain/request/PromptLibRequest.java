package cloud.cstream.chat.core.domain.request;

import cloud.cstream.chat.common.valid.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Data
public class PromptLibRequest {

    @NotNull(message = "请指定删除的记录", groups = {ValidGroup.Del.class})
    private Integer id;

    @NotBlank(message = "请输入标题")
    private String title;

    @NotBlank(message = "请输入提示词")
    private String promptContent;
}

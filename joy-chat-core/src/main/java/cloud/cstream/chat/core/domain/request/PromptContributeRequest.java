package cloud.cstream.chat.core.domain.request;

import cloud.cstream.chat.common.valid.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromptContributeRequest {

    @NotNull(message = "请指定要删除的提示词", groups = {ValidGroup.Del.class, ValidGroup.Update.class})
    private Integer id;


    @NotBlank(message = "请输入提示词标题", groups = {ValidGroup.Add.class})
    private String title;

    @NotBlank(message = "请输入提示词内容", groups = {ValidGroup.Add.class})
    private String promptContent;

}

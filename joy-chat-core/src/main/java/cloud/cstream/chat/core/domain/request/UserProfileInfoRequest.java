package cloud.cstream.chat.core.domain.request;

import cloud.cstream.chat.common.valid.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileInfoRequest {

    /**
     * 昵称
     */
    @NotBlank(message = "请输入新的昵称", groups = ValidGroup.Update.class)
    private String nickname;
    /**
     * 原密码
     */
    @NotBlank(message = "请输入原密码", groups = ValidGroup.Add.class)
    private String originalPwd;

    @NotBlank(message = "请输入新密码", groups = ValidGroup.Add.class)
    private String newPwd;

    @NotBlank(message = "请再次输入新密码", groups = ValidGroup.Add.class)
    private String repeatNewPwd;

}

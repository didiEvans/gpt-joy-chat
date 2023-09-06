package cloud.cstream.chat.core.domain.request;

import cloud.cstream.chat.common.valid.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysAdConfigRequest {

    @NotNull(message = "广告ID不能为空", groups = {ValidGroup.Update.class})
    private Integer id;
    /**
     * 封面图片
     */
    @NotBlank(message = "封面图片不能为空", groups = {ValidGroup.Add.class})
    private String coverPic;
    /**
     *  前端hover提示
     */
    private String hoverTips;
    /**
     * 重定向方式 0 不重定向, 1 站内路由, 2 外部链接
     */
    @NotNull(message = "重定向方式不能为空", groups = {ValidGroup.Add.class})
    private Integer redirectType;
    /**
     * 重定向地址
     */
    @NotBlank(message = "重定向地址不能为空", groups = {ValidGroup.Add.class})
    private String redirectAddress;
    /**
     * 是否启用
     */
    @NotNull(message = "是否启用不能为空", groups = {ValidGroup.Add.class})
    private Integer enable;
    /**
     * 备注
     */
    @NotNull(message = "备注不能为空", groups = {ValidGroup.Add.class})
    private String remark;
    /**
     * 由谁创建
     */
    private Integer createBy;

}

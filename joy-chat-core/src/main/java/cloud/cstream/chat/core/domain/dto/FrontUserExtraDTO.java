package cloud.cstream.chat.core.domain.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 前端用户邮箱登录表实体类
 *
 * @author CoDeleven
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FrontUserExtraDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 邮箱账号
     */
    private String username;

    /**
     * 加密后的密码
     */
    private String password;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
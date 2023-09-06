package cloud.cstream.chat.core.domain.entity;

import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
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
@TableName(value = "client_user_verify_info")
@Data
public class ClientUserVerifyInfoDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 加密盐
     */
    private String salt;
    /**
     * 是否验证过，false 否 true 是
     */
    private Boolean verified;
    /**
     * 验证类型
     */
    private ClientUserRegisterTypeEnum bizType;
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
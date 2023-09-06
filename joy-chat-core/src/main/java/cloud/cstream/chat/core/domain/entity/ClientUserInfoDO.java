package cloud.cstream.chat.core.domain.entity;

import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 前端用户 基础表实体类
 *
 * @author CoDeleven
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "client_user_info")
@Data
public class ClientUserInfoDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户描述
     */
    private String description;
    /**
     * 头像版本
     */
    private String avatarVersion;

    /**
     * 上一次登录 IP
     */
    private String lastLoginIp;
    /**
     * 邀请人id
     */
    private Integer inviteUid;
    /**
     * 邮箱地址
     */
    private String emailAddress;
    /**
     * 手机号码
     */
    private String mobilePhone;
    /**
     * 密码(MD5加密)
     */
    private String password;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 剩余会话次数
     */
    private Integer chatCount;
    /**
     * 注册方式
     */
    private ClientUserRegisterTypeEnum registryType;
    /**
     * 0 启用， 1 禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
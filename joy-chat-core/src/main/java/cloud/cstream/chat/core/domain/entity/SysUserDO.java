package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/5/14
 */
@Data
@TableName("sys_user")
public class SysUserDO {



    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

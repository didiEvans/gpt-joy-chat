package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_ad_config")
public class SysAdConfigDO {


    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 封面图片
     */
    private String coverPic;
    /**
     *  前端hover提示
     */
    private String hoverTips;
    /**
     * 重定向方式 0 不重定向, 1 站内路由, 2 外部链接
     */
    private Integer redirectType;
    /**
     * 重定向地址
     */
    private String redirectAddress;
    /**
     * 是否启用
     */
    private Integer enable;
    /**
     * 备注
     */
    private String remark;
    /**
     * 由谁创建
     */
    private Integer createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

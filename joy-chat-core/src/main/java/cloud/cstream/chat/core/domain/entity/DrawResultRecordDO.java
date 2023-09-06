package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 绘画记录记录
 *
 * @author ANker
 */
@Data
@Accessors(chain = true)
@TableName("draw_result_record")
public class DrawResultRecordDO {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; 
    /**
     * 归属任务id
     */
    private Integer taskId;
    /**
     * 绘图uuid
     */
    private String uuid;
    /**
     * 生成的图片地址
     */
    private String localImgPath;
    /**
     * 生成图片的源地址
     */
    private String originalImgPath;
    /**
     * 是否完成 0 未完成, 1 已完成
     */
    private Integer completed;
    /**
     * 1 常规已完成， 3 超时退款
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
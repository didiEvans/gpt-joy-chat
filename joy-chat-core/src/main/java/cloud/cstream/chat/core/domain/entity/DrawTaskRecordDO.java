package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * Ai绘图记录实体
 *
 * @author Anker
 */
@Data
@Accessors(chain = true)
@TableName("draw_task_record")
public class DrawTaskRecordDO {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 请求id
     */
    private String requestId;

    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 完成状态 0 未完成, 1 任务已完成
     */
    private Integer completeStatus;
    /**
     * 任务完成时间
     */
    private Date completeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}

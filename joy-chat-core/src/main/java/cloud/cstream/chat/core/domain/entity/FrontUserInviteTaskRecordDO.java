package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 邀请用户任务记录
 *
 * @author evans
 */
@Data
@TableName("front_user_invite_task_record")
public class FrontUserInviteTaskRecordDO {

    @TableId(type = IdType.AUTO)

    private Integer id;
    /**
     * 被邀请人id
     */
    private Integer inviteeUid;
    /**
     * 邀请人id
     */
    private Integer inviterUid;
    /**
     * 关联任务id
     * {@link cloud.cstream.chat.common.enums.InviteTaskStageEnum}
     */
    private Integer taskCode;
    /**
     * 是否已完成
     */
    private Boolean completed;
    /**
     * 完成时间
     */
    private Date completeTime;
    /**
     * 奖励会话次数
     */
    private Integer reward;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public FrontUserInviteTaskRecordDO(Integer inviteeUid, Integer inviterUid, Integer taskCode) {
        this.inviteeUid = inviteeUid;
        this.inviterUid = inviterUid;
        this.taskCode = taskCode;
    }
}

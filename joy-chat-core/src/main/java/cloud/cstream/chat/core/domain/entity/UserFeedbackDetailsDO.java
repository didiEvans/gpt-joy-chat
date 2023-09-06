package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("user_feedback_details")
public class UserFeedbackDetailsDO {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 反馈对话组id
     */
    private Integer groupId;

    /**
     * 反馈用户id
     */
    private Integer uid;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 系统经办人id
     */
    private Integer replaySysUid;

    /**
     * 0 用户反馈,1 系统回复
     */
    private Integer role;
    /**
     * 0 未读, 1 已读
     */
    private Integer read;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

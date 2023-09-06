package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;


@Data
public class UserFeedbackDetailsVO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 反馈对话组id
     */
    private Integer gId;

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
    private Boolean role;
    /**
     * 0 未读, 1 已读
     */
    private Date read;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}

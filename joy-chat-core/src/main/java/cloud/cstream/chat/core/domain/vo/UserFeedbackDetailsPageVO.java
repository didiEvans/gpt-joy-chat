package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Data
public class UserFeedbackDetailsPageVO {

    /**
     * id
     */
    private Integer id;
    /**
     * 反馈组id
     */
    private Integer groupId;
    /**
     * 角色
     */
    private Integer role;
    /**
     * 内容
     */
    private String content;
    /**
     * 时间
     */
    private Date createTime;

}

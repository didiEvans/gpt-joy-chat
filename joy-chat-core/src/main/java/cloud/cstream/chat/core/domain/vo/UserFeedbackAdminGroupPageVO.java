package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Data
public class UserFeedbackAdminGroupPageVO {


    /**
     * 组id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;
    /**
     * 提交时间
     */
    private Date createTime;
    /**
     * 是否存在未读，0 不存在， 1 存在
     */
    private Integer hasUnread;
    /**
     * 0 进行中， 1 已解决， -1 未解决
     */
    private Integer resolved;
    /**
     * 反馈人名称
     */
    private String feedbackUser;
    /**
     * 反馈人电话
     */
    private String feedbackUserPhone;
    /**
     * 反馈人邮箱
     */
    private String feedbackUserEmail;
}

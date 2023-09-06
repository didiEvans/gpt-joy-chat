package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Data
public class UserFeedbackGroupPageVO {


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
     * 未读回复数量
     */
    private Integer unreadCount;

    /**
     * 是否存在未读，0 不存在， 1 存在
     */
    private Integer hasRead;
    /**
     * 0 进行中， 1 已解决
     */
    private Integer resolved;

}

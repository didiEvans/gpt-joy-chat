package cloud.cstream.chat.core.domain.request;

import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Data
public class UserFeedbackSubmitRequest {

    /**
     * 反馈组id
     */
    private Integer groupId;
    /**
     * 简述
     */
    private String title;

    /**
     * 问题描述
     */
    private String content;


}

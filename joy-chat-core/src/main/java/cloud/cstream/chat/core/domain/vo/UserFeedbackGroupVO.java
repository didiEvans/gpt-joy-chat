package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFeedbackGroupVO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 反馈用户id
     */
    private Integer uid;

    /**
     * 标题
     */
    private String title;

    /**
     * 0 未解决, 1 已解决
     */
    private Boolean resolved;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}

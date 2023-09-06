package cloud.cstream.chat.core.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackPageQuery extends PageQuery{

    /**
     * 组id
     */
    private Integer gid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 是否已解决
     */
    private Integer resolved;

    /**
     * 只查询含有未读消息的反馈
     */
    private Boolean hasUnread;



}

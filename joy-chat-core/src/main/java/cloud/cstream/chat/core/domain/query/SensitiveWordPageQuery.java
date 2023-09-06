package cloud.cstream.chat.core.domain.query;

import cloud.cstream.chat.common.enums.EnableDisableStatusEnum;
import cloud.cstream.chat.core.domain.query.PageQuery;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Anker
 * @date 2023/3/28 21:01
 * 敏感词分页查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SensitiveWordPageQuery extends PageQuery {
    /**
     * 敏感词内容
     */
    @Size(max = 20, message = "敏感词内容不超过 20 个字")
    private String word;

    /**
     * 状态 1 启用 2 停用
     */
    private EnableDisableStatusEnum status;
}

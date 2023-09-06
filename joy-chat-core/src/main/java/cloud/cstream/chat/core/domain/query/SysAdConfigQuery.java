package cloud.cstream.chat.core.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysAdConfigQuery extends PageQuery{

    /**
     * 重定向方式 0 不重定向, 1 站内路由, 2 外部链接
     */
    private Integer redirectType;
    /**
     * 是否启用
     */
    private Integer enable;

}

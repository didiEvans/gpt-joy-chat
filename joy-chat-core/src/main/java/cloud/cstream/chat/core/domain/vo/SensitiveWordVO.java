package cloud.cstream.chat.core.domain.vo;

import cloud.cstream.chat.common.enums.EnableDisableStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/3/28 21:03
 * 敏感词展示参数
 */
@Data
public class SensitiveWordVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 敏感词内容
     */
    private String word;

    /**
     * 状态 1 启用 2 停用
     */
    private EnableDisableStatusEnum status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}

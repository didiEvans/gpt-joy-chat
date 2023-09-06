package cloud.cstream.chat.core.domain.entity;

import cloud.cstream.chat.common.enums.EnableDisableStatusEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/3/28 20:43
 * 敏感词表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sensitive_word")
public class SensitiveWordDO {

    public SensitiveWordDO(String word) {
        this.word = word;
        this.status = EnableDisableStatusEnum.ENABLE;
    }

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 敏感词内容
     */
    private String word;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 状态 1 启用 2 停用
     */
    private EnableDisableStatusEnum status;

    /**
     * 是否删除 0 否 NULL 是
     */
    @TableLogic(value = "0", delval = "NULL")
    private Integer isDeleted;

}

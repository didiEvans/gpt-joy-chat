package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/7/9
 */
@Data
@Builder
@TableName("sensitive_word_trigger_record")
public class SensitiveWordTriggerRecord {


    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 敏感词
     */
    private String word;
    /**
     * 会话id
     */
    private String conversationId;
    /**
     * 对话内容
     */
    private String content;
    /**
     * 对话内容
     */
    private String sysContent;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Date createTime;

}

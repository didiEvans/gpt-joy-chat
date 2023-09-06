package cloud.cstream.chat.core.domain.entity;

import cloud.cstream.chat.common.enums.ApiTypeEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Anker
 * @date 2023/3/25 16:14
 * 聊天室表实体类
 */
@Data
@TableName("chat_room")
public class ChatRoomDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户 id
     */
    private Integer userId;

    /**
     * 对话 id
     * 唯一
     */
    private String conversationId;

    /**
     * ip
     */
    private String ip;

    /**
     * 第一条消息主键
     * 唯一
     */
    private Long firstChatMessageId;

    /**
     * 第一条消息 id
     * 唯一
     */
    private String firstMessageId;

    /**
     * 对话标题
     */
    private String title;

    /**
     * API 类型
     * 不同类型的对话不能一起存储
     */
    private ApiTypeEnum apiType;

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
}

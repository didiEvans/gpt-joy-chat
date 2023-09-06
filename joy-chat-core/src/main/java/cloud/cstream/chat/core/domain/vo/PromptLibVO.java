package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Data
public class PromptLibVO {


    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 提示词内容
     */
    private String promptContent;
    /**
     * 贡献人
     */
    private String contributor;
    /**
     * 创建时间
     */
    private Date createTime;

}

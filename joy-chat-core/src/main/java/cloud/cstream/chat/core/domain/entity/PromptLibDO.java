package cloud.cstream.chat.core.domain.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 提示词库
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("prompt_lib")
public class PromptLibDO {

    public PromptLibDO(String title, String promptContent, Integer type, Integer contributeUid) {
        this.title = title;
        this.promptContent = promptContent;
        this.summary = StrUtil.sub(promptContent,0, 168);
        this.type = type;
        this.contributeUid = contributeUid;
    }

    @TableId(type = IdType.AUTO)
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
     * 为1时表示该提示词为工具
     */
    private Integer toolFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 0 系统导入, 1 用户贡献
     */
    private Integer type;
    /**
     * 贡献人id 0 为管理员导入
     */
    private Integer contributeUid;
}

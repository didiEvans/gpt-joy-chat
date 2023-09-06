package cloud.cstream.chat.core.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/7/9
 */
@Data
@Builder
public class SysPromptInfo {

    /**
     * 系统提示词
     */
    private String sysPromptContent;

    /**
     * 是否需要对内容进行审核
     */
    private boolean reviewSysPrompt;


}

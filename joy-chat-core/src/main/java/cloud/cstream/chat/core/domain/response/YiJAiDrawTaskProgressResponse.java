package cloud.cstream.chat.core.domain.response;

import lombok.Data;

@Data
public class YiJAiDrawTaskProgressResponse {
    /**
     * YJ绘图任务id
     */
    private String Uuid;
    /**
     * // 百分比进度，只有Status为2（进行中）,进度才有意义
     */
    private Integer Progress;

    private String ImagePath;

    /**
     * 0 表示排队中, 1 表示已经完成,  2表示运行中  3 超时退款  4.用户主动取消
     */
    private Integer Status;

}

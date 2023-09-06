package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 绘画记录记录
 *
 * @author ANker
 */
@Data
public class DrawResultRecordVO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 绘图uuid
     */
    private String uuid;

    /**
     * 生成图片的源地址
     */
    private String originalImgPath;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 任务的请求id
     */
    private String requestId;
    /**
     * 1 已完成  2 进行中  3 超时退款
     */
    private Integer status;


}
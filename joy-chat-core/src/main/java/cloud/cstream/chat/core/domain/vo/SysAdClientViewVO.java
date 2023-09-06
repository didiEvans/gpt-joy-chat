package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

@Data
public class SysAdClientViewVO {

    private Integer id;
    /**
     * 封面图片
     */
    private String coverPic;
    /**
     *  前端hover提示
     */
    private String hoverTips;
    /**
     * 重定向方式 0 不重定向, 1 站内路由, 2 外部链接
     */
    private Integer redirectType;
    /**
     * 重定向地址
     */
    private String redirectAddress;

}

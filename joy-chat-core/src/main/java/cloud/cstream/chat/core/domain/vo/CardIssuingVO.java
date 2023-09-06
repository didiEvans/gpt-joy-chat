package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 发卡卡密对实体
 *
 * @author evans
 * @description
 * @date 2023/5/24
 */
@Data
public class CardIssuingVO {

    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡密
     */
    private String cardPwd;
    /**
     * 对应增值服务
     */
    private String vasPkgName;
    /**
     * 创建人id
     */
    private String createUsername;
    /**
     * 是否已被兑换
     */
    private String convertStatus;
    /**
     * 兑换用户
     */
    private String convertUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 兑换时间
     */
    private Date convertTime;

}

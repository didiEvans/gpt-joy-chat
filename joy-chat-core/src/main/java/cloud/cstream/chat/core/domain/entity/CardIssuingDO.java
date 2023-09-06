package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
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
@Builder
@TableName("card_issuing")
public class CardIssuingDO {


    @TableId(type = IdType.AUTO)
    private Integer id;

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
    private Integer vasPkgId;
    /**
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 是否已被兑换
     */
    private Integer converted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

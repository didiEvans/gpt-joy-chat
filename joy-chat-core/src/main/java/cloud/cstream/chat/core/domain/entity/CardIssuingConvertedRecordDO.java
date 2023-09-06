package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 发卡卡密兑换记录
 *
 * @author evans
 * @description
 * @date 2023/5/24
 */
@Data
@Builder
@TableName("card_issuing_converted_record")
public class CardIssuingConvertedRecordDO {


    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 卡密id
     */
    private Integer cardId;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 兑换人
     */
    private Integer convertedUid;
    /**
     * 兑换时间
     */
    private Date convertedTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

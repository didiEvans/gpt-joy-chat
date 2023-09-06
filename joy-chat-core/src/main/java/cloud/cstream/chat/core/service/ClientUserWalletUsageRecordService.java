package cloud.cstream.chat.core.service;

import cloud.cstream.chat.common.enums.AmountType;
import cloud.cstream.chat.common.enums.BizType;
import cloud.cstream.chat.common.enums.DirectionTypeEnum;
import cloud.cstream.chat.core.domain.entity.ClientUserWalletUsageRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

public interface ClientUserWalletUsageRecordService extends IService<ClientUserWalletUsageRecordDO> {



    void record(Integer userId, BigDecimal amount, AmountType amountType, DirectionTypeEnum directionType, BizType bizType, String remark);




}

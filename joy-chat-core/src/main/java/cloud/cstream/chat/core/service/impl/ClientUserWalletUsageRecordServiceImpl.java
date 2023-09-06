package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.enums.AmountType;
import cloud.cstream.chat.common.enums.BizType;
import cloud.cstream.chat.common.enums.DirectionTypeEnum;
import cloud.cstream.chat.core.domain.entity.ClientUserWalletUsageRecordDO;
import cloud.cstream.chat.core.mapper.ClientUserWalletUsageRecordMapper;
import cloud.cstream.chat.core.service.ClientUserWalletUsageRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ClientUserWalletUsageRecordServiceImpl extends ServiceImpl<ClientUserWalletUsageRecordMapper, ClientUserWalletUsageRecordDO> implements ClientUserWalletUsageRecordService {


    @Override
    public void record(@NotNull Integer userId, @NotNull BigDecimal amount, @NotNull AmountType amountType, @NotNull DirectionTypeEnum directionType, BizType bizType, String remark) {
        ClientUserWalletUsageRecordDO record = ClientUserWalletUsageRecordDO.builder()
                .uid(userId)
                .amount(amount)
                .amountType(amountType.getType())
                .direction(directionType.getType())
                .bizType(bizType.getType())
                .remark(remark).build();
        this.save(record);
    }
}

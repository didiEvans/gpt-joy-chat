package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.enums.AmountType;
import cloud.cstream.chat.common.enums.BizType;
import cloud.cstream.chat.common.enums.DirectionTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.ClientUserWalletDO;
import cloud.cstream.chat.core.mapper.ClientUserWalletMapper;
import cloud.cstream.chat.core.service.ClientUserWalletService;
import cloud.cstream.chat.core.service.ClientUserWalletUsageRecordService;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ClientUserWalletServiceImpl extends ServiceImpl<ClientUserWalletMapper, ClientUserWalletDO> implements ClientUserWalletService {


    @Autowired
    private ClientUserWalletUsageRecordService walletUsageRecordService;


    @Override
    public ClientUserWalletDO getUserWallet(Integer userId) {
        ClientUserWalletDO walletDO = baseMapper.selectByUid(userId);
        if (Objects.nonNull(walletDO)){
            return walletDO;
        }
        walletDO = ClientUserWalletDO.builder().uid(userId).points(NumConstant.FIVE).build();
        save(walletDO);
        return walletDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pointRecharge(Integer userId, BigDecimal amount) {
        ClientUserWalletDO userWallet = this.getUserWallet(userId);
        Integer points = userWallet.getPoints() + amount.intValue();
        userWallet.setPoints(points);
        updateById(userWallet);
        walletUsageRecordService.record(userId, amount, AmountType.POINTS, DirectionTypeEnum.IN, BizType.DRAW, "入账"+ amount.intValue() +"积分");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pointDeduction(Integer userId, BigDecimal amount) {
        ClientUserWalletDO userWallet = this.getUserWallet(userId);
        int points = userWallet.getPoints() - amount.intValue();
        if (points < 0){
            throw new ServiceException("积分余额不足,请充值");
        }
        userWallet.setPoints(points);
        updateById(userWallet);
        walletUsageRecordService.record(userId, amount, AmountType.POINTS, DirectionTypeEnum.OUT, BizType.DRAW, "消耗"+ amount.intValue() +"积分");
    }

    @Override
    public void initUserWallet(Integer userId) {
        getUserWallet(userId);
    }

    @Override
    public boolean userBalanceIsEnough(Integer uid, Integer expendPoints) {
        ClientUserWalletDO userWallet = getUserWallet(uid);
        BigDecimal balance = NumberUtil.sub(userWallet.getPoints(), expendPoints);
        return balance.compareTo(BigDecimal.ZERO) < 0;
    }
}

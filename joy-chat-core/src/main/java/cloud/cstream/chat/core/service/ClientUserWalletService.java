package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.ClientUserWalletDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

public interface ClientUserWalletService extends IService<ClientUserWalletDO> {

    /**
     * 用户钱包初始化
     *
     * @param userId 用户id
     * @return {@link  ClientUserWalletDO}
     */
    ClientUserWalletDO getUserWallet(Integer userId);


    /**
     * 积分充值
     *
     * @param userId 用户id
     * @param amount 充值金额
     */
    void pointRecharge(Integer userId, BigDecimal amount);

    /**
     * 积分扣减
     *
     * @param userId 用户id
     * @param amount 金额
     *               积分不足时,会抛出异常
     */
    void pointDeduction(Integer userId, BigDecimal amount);

    /**
     * 初始化用户钱包
     *
     * @param id
     */
    void initUserWallet(Integer id);

    /**
     * 计算用户积分余额是否充足
     *
     * @param uid   用户id
     * @param compute   消耗积分
     * @return true 足够， false 不够
     */
    boolean userBalanceIsEnough(Integer uid, Integer compute);
}

package cloud.cstream.chat.core.strategy.sms;

import cloud.cstream.chat.common.constants.RedisKeyConstant;
import cloud.cstream.chat.common.enums.SmsSendHandlerEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.SysSmsConfigDO;
import cloud.cstream.chat.core.mapper.SysSmsConfigMapper;
import cloud.cstream.chat.core.strategy.sms.impl.TencentSmsSendHandlerImpl;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
public abstract class AbstractSendSmsTypeStrategy {

    @Autowired
    private SysSmsConfigMapper smsConfigMapper;
    @Autowired
    private RedisClient redisClient;

    /**
     * 根据注册类型获取逻辑处理策略
     *
     * @param handlerEnum 注册类型
     * @return 策略
     */
    public static AbstractSendSmsTypeStrategy findStrategyHandler(SmsSendHandlerEnum handlerEnum) {
        switch (handlerEnum) {
            // 腾讯云
            case TENCENT -> {
                return SpringUtil.getBean(TencentSmsSendHandlerImpl.class);
            }
            default -> {
                break;
            }
        }
        throw new ServiceException("暂未接入该短信平台");
    }

    protected SysSmsConfigDO getSmsConfig(SmsSendHandlerEnum handlerEnum){
        String cache = String.format(RedisKeyConstant.SYS_SMS_CONFIG, handlerEnum.getHandlerCode());
        SysSmsConfigDO configDO = redisClient.get(cache, SysSmsConfigDO.class);
        if (Objects.isNull(configDO)){
            configDO = smsConfigMapper.selectOne(Wrappers.<SysSmsConfigDO>lambdaQuery().eq(SysSmsConfigDO::getHandlerCode, handlerEnum.getHandlerCode()));
            redisClient.set(cache, JSONUtil.toJsonStr(configDO));
        }
        return configDO;
    }


    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @param captcha 验证码
     * @return 成功与否
     */
    public abstract boolean sendCaptcha(String phone, String captcha);


}

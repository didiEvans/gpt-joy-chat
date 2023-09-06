package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.exception.ArrivalLimitException;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.VasPkgDO;
import cloud.cstream.chat.core.service.ChatSessionCountLimitService;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static cloud.cstream.chat.common.constants.RedisKeyConstant.USER_FREE_SESSION_COUNT_CACHE;
import static cloud.cstream.chat.common.constants.RedisKeyConstant.USER_VAS_SESSION_LIMIT_CACHE;

/**
 * @author evans
 * @description
 * @date 2023/5/9
 */
@Service
public class ChatSessionCountLimitServiceImpl implements ChatSessionCountLimitService {

    @Autowired
    private RedisClient redisClient;


    @Override
    public void isReachTheFreeLimit(ClientUserInfoDO userInfo) {
        String cacheKey = String.format(USER_FREE_SESSION_COUNT_CACHE, DatePattern.PURE_DATE_FORMAT.format(DateUtil.date()), userInfo.getId());
        Integer sessionCount = redisClient.getInt(cacheKey);
        if (Objects.isNull(sessionCount)){
            sessionCount = NumConstant.INT_ZERO;
            redisClient.setEx(cacheKey, sessionCount,1,TimeUnit.DAYS);
        }
        if (NumberUtil.compare(sessionCount, userInfo.getChatCount()) >= 0 ){
            throw new ArrivalLimitException("今日会话额度已用完,购买增值套餐可享受不限次对话");
        }
        redisClient.incrBy(cacheKey, NumConstant.INT_ONE);
    }

    @Override
    public void checkReachTheConfLimit(VasPkgDO currentVasPkg) {
        String cacheKey = String.format(USER_VAS_SESSION_LIMIT_CACHE, currentVasPkg.getId());
        Integer sessionCount = redisClient.getInt(cacheKey);
        if (Objects.isNull(sessionCount)){
            redisClient.setEx(cacheKey, NumConstant.INT_ONE, currentVasPkg.getConfTimeInterval(), TimeUnit.HOURS);
            sessionCount = NumConstant.INT_ZERO;
        }
        if (NumberUtil.compare(sessionCount, currentVasPkg.getConfLimit()) >= 0){
            throw new ServiceException("已达"+ currentVasPkg.getConfTimeInterval() + "小时会话上限, 请稍后再试!");
        }
        redisClient.incrBy(cacheKey, NumConstant.INT_ONE);
    }
}

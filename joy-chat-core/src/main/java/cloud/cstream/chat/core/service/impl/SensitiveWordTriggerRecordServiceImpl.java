package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.constants.RedisKeyConstant;
import cloud.cstream.chat.core.domain.entity.SensitiveWordTriggerRecord;
import cloud.cstream.chat.core.mapper.SensitiveWordTriggerRecordMapper;
import cloud.cstream.chat.core.service.SensitiveWordTriggerRecordService;
import cloud.cstream.chat.core.utils.RedisClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author evans
 * @description
 * @date 2023/7/9
 */
@Service
public class SensitiveWordTriggerRecordServiceImpl extends ServiceImpl<SensitiveWordTriggerRecordMapper, SensitiveWordTriggerRecord> implements SensitiveWordTriggerRecordService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public boolean hitSensitiveWordLimit(Integer userId, boolean increment) {
        String redisKey = String.format(RedisKeyConstant.USER_HIT_SENSITIVE_WORD_COUNT, userId);
        if (increment){
            redisClient.incrBy(redisKey, 1);
            redisClient.expire(redisKey, 30, TimeUnit.MINUTES);
            return false;
        }
        Integer hitCount = redisClient.getInt(redisKey);
        if (Objects.isNull(hitCount)){
            return false;
        }
        return NumConstant.FIVE.compareTo(hitCount) < 0;
    }
}

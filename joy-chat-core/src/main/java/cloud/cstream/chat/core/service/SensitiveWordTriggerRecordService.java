package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.SensitiveWordTriggerRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author evans
 * @description
 * @date 2023/7/9
 */
public interface SensitiveWordTriggerRecordService extends IService<SensitiveWordTriggerRecord> {
    /**
     * 单位时间内是否命中敏感词上限
     *
     * @param userId
     * @param increment
     * @return
     */
    boolean hitSensitiveWordLimit(Integer userId, boolean increment);
}

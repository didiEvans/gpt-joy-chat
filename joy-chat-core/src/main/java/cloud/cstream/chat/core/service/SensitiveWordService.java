package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/28 20:46
 * 敏感词业务接口
 */
public interface SensitiveWordService extends IService<SensitiveWordDO> {

    int batchInsert(List<SensitiveWordDO> words);

}

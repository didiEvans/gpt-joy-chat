package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import cloud.cstream.chat.core.mapper.SensitiveWordMapper;
import cloud.cstream.chat.core.service.SensitiveWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/28 20:47
 * 敏感词业务实现类
 */
@Service("CommonSensitiveWordServiceImpl")
public class SensitiveWordServiceImpl  extends ServiceImpl<SensitiveWordMapper, SensitiveWordDO> implements SensitiveWordService {


    @Override
    public int batchInsert(List<SensitiveWordDO> words) {
        return baseMapper.batchInsert(words);
    }
}

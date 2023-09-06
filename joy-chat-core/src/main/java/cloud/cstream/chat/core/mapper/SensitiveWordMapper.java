package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/28 20:47
 * 敏感词数据库访问层
 */
public interface SensitiveWordMapper extends BaseMapper<SensitiveWordDO> {
    /**
     * 批量插入
     *
     * @param dos
     * @return
     */
    int batchInsert(@Param("dos") List<SensitiveWordDO> dos);
}
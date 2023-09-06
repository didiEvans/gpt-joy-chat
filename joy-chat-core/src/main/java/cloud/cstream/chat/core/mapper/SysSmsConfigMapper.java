package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.SysSmsConfigDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Repository
public interface SysSmsConfigMapper extends BaseMapper<SysSmsConfigDO> {
}

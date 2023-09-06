package cloud.cstream.chat.admin.converter;

import cloud.cstream.chat.core.domain.vo.SensitiveWordVO;
import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/28 23:11
 * 敏感词相关转换
 */
@Mapper
public interface SensitiveWordConverter {

    SensitiveWordConverter INSTANCE = Mappers.getMapper(SensitiveWordConverter.class);

    /**
     * entityToVO
     *
     * @param sensitiveWordDOList sensitiveWordDOList
     * @return List<SensitiveWordVO>
     */
    List<SensitiveWordVO> entityToVO(List<SensitiveWordDO> sensitiveWordDOList);
}

package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.DrawResultRecordDO;
import cloud.cstream.chat.core.domain.vo.DrawResultRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/7/8
 */
public interface DrawResultRecordMapper extends BaseMapper<DrawResultRecordDO> {
    Page<DrawResultRecordVO> queryDrawHistoryPage(Page<Object> page, @Param("userId") Integer userId);

    List<String> selectUncompletedDrawJobUuids(@Param("userId") Integer userId);
}

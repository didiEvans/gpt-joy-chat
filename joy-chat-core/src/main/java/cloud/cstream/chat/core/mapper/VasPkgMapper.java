package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.VasPkgDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface VasPkgMapper extends BaseMapper<VasPkgDO> {
    /**
     * 变更对话套餐状态
     *
     * @param targetStatus
     */
    void updateAllChatVasPkgToTargetStatus(@Param("targetStatus") Integer targetStatus);

    Integer queryChatVasPkgEnableStatus();

}

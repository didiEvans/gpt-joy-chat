package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.VasOrderDO;
import cloud.cstream.chat.core.domain.query.VasOrderPageQuery;
import cloud.cstream.chat.core.domain.vo.VasOrderPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface VasOrderMapper extends BaseMapper<VasOrderDO> {
    Page<VasOrderPageVO> queryPage(Page<Object> toPage, @Param("query") VasOrderPageQuery pageQuery);
}

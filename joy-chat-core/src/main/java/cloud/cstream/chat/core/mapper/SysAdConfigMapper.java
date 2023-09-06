package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.SysAdConfigDO;
import cloud.cstream.chat.core.domain.query.SysAdConfigQuery;
import cloud.cstream.chat.core.domain.vo.SysAdClientViewVO;
import cloud.cstream.chat.core.domain.vo.SysAdConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统广告配置Mapper接口
 *
 * @author Anker
 */
public interface SysAdConfigMapper extends BaseMapper<SysAdConfigDO> {

    Page<SysAdConfigVO> queryPage(Page<Object> toPage, @Param("query") SysAdConfigQuery pageQuery);

    /**
     * 查询开启的广告列表
     *
     * @return
     */
    List<SysAdClientViewVO> selectAdList();

}

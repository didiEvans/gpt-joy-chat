package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.CardIssuingDO;
import cloud.cstream.chat.core.domain.query.CardIssuingPageQuery;
import cloud.cstream.chat.core.domain.vo.CardIssuingVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/5/24
 */
public interface CardIssuingMapper extends BaseMapper<CardIssuingDO> {
    /**
     * 批量插入
     *
     * @param cardPwdPairDO
     */
    void batchInsert(@Param("dos") List<CardIssuingDO> cardPwdPairDO);

    Page<CardIssuingVO> pageQuery(Page<Object> toPage, @Param("query") CardIssuingPageQuery query);
}

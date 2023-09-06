package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.PromptLibDO;
import cloud.cstream.chat.core.domain.query.PromptPageQuery;
import cloud.cstream.chat.core.domain.vo.PromptLibVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提示词库db映射器
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */

public interface PromptLibMapper extends BaseMapper<PromptLibDO> {
    /**
     * 分页查询
     * @param page
     * @param pageQuery
     * @return
     */
    Page<PromptLibVO> pageQuery(Page<Object> page, @Param("query") PromptPageQuery pageQuery);

    List<PromptLibVO> selectToolboxPrompt();

    /**
     * 批量插入
     *
     * @param promptList
     */
    void batchInsert(@Param("coll") List<PromptLibDO> promptList);

    /**
     * 查询客户端用户自有贡献词
     *
      * @param toPage
     * @param query
     * @return
     */
    Page<PromptLibVO> selectClientUserToolPage(Page<Object> toPage, @Param("query") PromptPageQuery query);

    /**
     *
     *
     * @param id
     * @param userId
     * @return
     */
    PromptLibDO selectByUserIdAndPk(@Param("id") Integer id,@Param("userId") Integer userId);
}

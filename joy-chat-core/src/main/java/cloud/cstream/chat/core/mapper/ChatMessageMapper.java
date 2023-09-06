package cloud.cstream.chat.core.mapper;


import cloud.cstream.chat.core.domain.entity.ChatMessageDO;
import cloud.cstream.chat.core.domain.query.ChatMessagePageQuery;
import cloud.cstream.chat.core.domain.vo.ChatMessageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author Anker
 * @date 2023/4/22 20:08
 * 聊天记录数据访问层
 */
public interface ChatMessageMapper extends BaseMapper<ChatMessageDO> {
    /**
     * 查询对话分页聊天数据
     *
     * @param page          分页对象
     * @param pageQuery     分页参数查询条件
     * @return {@link  Page<ChatMessageVO>} 分页结果
     */
    Page<ChatMessageVO> queryPage(Page<Object> page, @Param("query") ChatMessagePageQuery pageQuery);
}

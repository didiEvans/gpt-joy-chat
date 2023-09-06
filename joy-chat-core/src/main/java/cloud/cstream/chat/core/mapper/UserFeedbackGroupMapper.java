package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.UserFeedbackGroupDO;
import cloud.cstream.chat.core.domain.query.FeedbackPageQuery;
import cloud.cstream.chat.core.domain.vo.UserFeedbackAdminGroupPageVO;
import cloud.cstream.chat.core.domain.vo.UserFeedbackGroupPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackGroupMapper extends BaseMapper<UserFeedbackGroupDO> {
    /**
     * 查询组分页
     *
     * @param userId
     * @return
     */
    Page<UserFeedbackGroupPageVO> queryGroupPage(Page<Object> page, @Param("userId") Integer userId);


    Page<UserFeedbackAdminGroupPageVO> userFeedbackPage(Page<Object> toPage, @Param("query") FeedbackPageQuery query);
}

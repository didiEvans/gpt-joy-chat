package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.FrontUserInviteTaskRecordDO;
import cloud.cstream.chat.core.domain.query.InviteePageQuery;
import cloud.cstream.chat.core.domain.vo.UserInviteeListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 邀请任务记录表映射器
 *
 * @author evans
 */
public interface FrontUserInviteTaskRecordMapper extends BaseMapper<FrontUserInviteTaskRecordDO> {
    /**
     * 查询邀请用户数
     *
     * @param baseUserId
     * @return
     */
    Integer selectInviteUserCount(@Param("uid") Integer baseUserId);

    /**
     * 查询被邀请人列表
     *
     * @param toPage
     * @param pageQuery
     * @return
     */
    Page<UserInviteeListVO> queryInviteeList(Page<Object> toPage, @Param("query") InviteePageQuery pageQuery);
}

package cloud.cstream.chat.core.service;

import cloud.cstream.chat.common.enums.InviteTaskStageEnum;
import cloud.cstream.chat.core.domain.entity.FrontUserInviteTaskRecordDO;
import cloud.cstream.chat.core.domain.query.InviteePageQuery;
import cloud.cstream.chat.core.domain.vo.UserInviteeListVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户邀请任务记录
 *
 * @author evans
 */
public interface FrontUserInviteTaskRecordService extends IService<FrontUserInviteTaskRecordDO> {

    /**
     * 初始化邀请任务记录
     *
     * @param inviterUid 邀请人id
     * @param inviteeUid 被邀请人id
     */
    void initInviteRecords(Integer inviterUid, Integer inviteeUid);

    /**
     * 尝试完成任务
     *
     * @param inviteTaskStageEnum   任务阶段
     * @param inviterUid            邀请人
     * @param inviteeUid            被邀请人
     */
    void tryCompleteTaskStage(InviteTaskStageEnum inviteTaskStageEnum, Integer inviterUid, Integer inviteeUid);

    /**
     * 尝试完成任务
     *
     * @param inviteTaskStageEnum 任务阶段
     * @param inviteeUid        被邀请人
     */
    void tryCompleteTaskStage(InviteTaskStageEnum inviteTaskStageEnum, Integer inviteeUid);

    /**
     * 查询邀请用户数
     *
     * @param baseUserId
     * @return
     */
    Integer selectInviteUserCount(Integer baseUserId);

    Page<UserInviteeListVO> queryInviteeList(InviteePageQuery pageQuery);
}

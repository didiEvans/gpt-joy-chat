package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.UserFeedbackDetailsDO;
import cloud.cstream.chat.core.domain.request.UserFeedbackSubmitRequest;
import cloud.cstream.chat.core.domain.vo.UserFeedbackDetailsPageVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserFeedbackDetailsService extends IService<UserFeedbackDetailsDO> {
    /**
     * 用户回复
     *
     * @param request
     * @param userId
     */
    void userReply(UserFeedbackSubmitRequest request, Integer userId);

    List<UserFeedbackDetailsPageVO> queryDetailsList(Integer gid, Integer userId);

    Integer queryUnReadMsgCount(Integer userId);

    List<UserFeedbackDetailsPageVO> adminFeedbackDetails(Integer gId);

    void adminReplyUser(UserFeedbackSubmitRequest request, Integer sysUserId);
}

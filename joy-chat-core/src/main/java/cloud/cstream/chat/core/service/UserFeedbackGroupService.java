package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.UserFeedbackGroupDO;
import cloud.cstream.chat.core.domain.query.FeedbackPageQuery;
import cloud.cstream.chat.core.domain.query.PageQuery;
import cloud.cstream.chat.core.domain.request.UserFeedbackSubmitRequest;
import cloud.cstream.chat.core.domain.vo.UserFeedbackAdminGroupPageVO;
import cloud.cstream.chat.core.domain.vo.UserFeedbackGroupPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserFeedbackGroupService extends IService<UserFeedbackGroupDO> {
    /**
     * 客户提交反馈
     *
     * @param request
     * @param userId
     * @return
     */
    void submit(UserFeedbackSubmitRequest request, Integer userId);

    /**
     * 反馈组分页
     *
     * @param query
     * @param userId
     * @return
     */
    Page<UserFeedbackGroupPageVO> queryGroupPage(PageQuery query, Integer userId);

    Page<UserFeedbackAdminGroupPageVO> userFeedbackPage(FeedbackPageQuery query);

    void resolved(Integer gId);
}

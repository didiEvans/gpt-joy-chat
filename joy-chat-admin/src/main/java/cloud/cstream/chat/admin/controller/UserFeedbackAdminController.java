package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.common.util.StpAdminUtil;
import cloud.cstream.chat.core.domain.query.FeedbackPageQuery;
import cloud.cstream.chat.core.domain.request.UserFeedbackQueryRequest;
import cloud.cstream.chat.core.domain.request.UserFeedbackSubmitRequest;
import cloud.cstream.chat.core.domain.vo.UserFeedbackAdminGroupPageVO;
import cloud.cstream.chat.core.domain.vo.UserFeedbackDetailsPageVO;
import cloud.cstream.chat.core.service.UserFeedbackDetailsService;
import cloud.cstream.chat.core.service.UserFeedbackGroupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * admin-用户反馈管理端控制器
 *
 * @author Anker
 */
@RestController
@RequestMapping("admin/user_feedback")
public class UserFeedbackAdminController {


    @Autowired
    private UserFeedbackGroupService feedbackGroupService;
    @Autowired
    private UserFeedbackDetailsService feedbackDetailsService;


    @PostMapping("feedback_page")
    public R<Page<UserFeedbackAdminGroupPageVO>> userFeedbackPage(@RequestBody FeedbackPageQuery query){
        return R.data(feedbackGroupService.userFeedbackPage(query));
    }

    @PostMapping("feedback_details")
    public R<List<UserFeedbackDetailsPageVO>> adminFeedbackDetails(@RequestBody UserFeedbackQueryRequest query){
        return R.data(feedbackDetailsService.adminFeedbackDetails(query.getGroupId()));
    }

    @PostMapping("replay")
    public R<Void> adminReplyUser(@RequestBody UserFeedbackSubmitRequest request){
        feedbackDetailsService.adminReplyUser(request, StpAdminUtil.getLoginId());
        return R.success();
    }


    @PostMapping("resolved")
    public R<Void> resolved(@RequestBody UserFeedbackSubmitRequest request){
        feedbackGroupService.resolved(request.getGroupId());
        return R.success();
    }




}

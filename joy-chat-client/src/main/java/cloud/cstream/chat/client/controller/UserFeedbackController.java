package cloud.cstream.chat.client.controller;

import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.domain.query.PageQuery;
import cloud.cstream.chat.core.domain.request.UserFeedbackSubmitRequest;
import cloud.cstream.chat.core.domain.vo.UserFeedbackDetailsPageVO;
import cloud.cstream.chat.core.domain.vo.UserFeedbackGroupPageVO;
import cloud.cstream.chat.core.service.UserFeedbackDetailsService;
import cloud.cstream.chat.core.service.UserFeedbackGroupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * client-反馈相关接口
 *
 * @author evans
 * @description
 * @date 2023/7/17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("client/feedback")
public class UserFeedbackController {


    private final UserFeedbackGroupService feedbackGroupService;

    private final UserFeedbackDetailsService feedbackDetailsService;




    /**
     * 提交反馈
     *
     * @param request 请求参数
     * @return
     */
    @PostMapping("/submit")
    public R<Page<UserFeedbackGroupPageVO>> submit(@RequestBody UserFeedbackSubmitRequest request) {
        feedbackGroupService.submit(request, ClientUserLoginUtil.getUserId());
        return R.success();
    }

    /**
     * 客户端回复
     *
     * @param request
     * @return
     */
    @PostMapping("reply")
    public R<Void> userReply(@RequestBody UserFeedbackSubmitRequest request) {
        feedbackDetailsService.userReply(request, ClientUserLoginUtil.getUserId());
        return R.success();
    }

    /**
     * 反馈组分页
     *
     * @return
     */
    @PostMapping("/group_page")
    public R<Page<UserFeedbackGroupPageVO>> queryGroupPage(@RequestBody PageQuery query) {
        return R.data(feedbackGroupService.queryGroupPage(query, ClientUserLoginUtil.getUserId()));
    }

    /**
     * 回复详情
     *
     * @param request
     * @return
     */
    @PostMapping("/g_details")
    public R<List<UserFeedbackDetailsPageVO>> queryDetailsList(@RequestParam("groupId") Integer gid) {
        return R.data(feedbackDetailsService.queryDetailsList(gid, ClientUserLoginUtil.getUserId()));
    }

    /**
     *未读消息数量
     *
     * @return 消息数量
     */
    @GetMapping("/msg_count")
    public R<Integer> msgCount(){
        return R.data(feedbackDetailsService.queryUnReadMsgCount(ClientUserLoginUtil.getUserId()));
    }

    /**
     * 解决
     * @param request
     * @return
     */
    @PostMapping("/resolved")
    public R<Void> resolved(@RequestBody UserFeedbackSubmitRequest request){
        feedbackGroupService.resolved(request.getGroupId());
        return R.success();
    }

}

package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.SysTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.UserFeedbackDetailsDO;
import cloud.cstream.chat.core.domain.entity.UserFeedbackGroupDO;
import cloud.cstream.chat.core.domain.request.UserFeedbackSubmitRequest;
import cloud.cstream.chat.core.domain.vo.UserFeedbackDetailsPageVO;
import cloud.cstream.chat.core.handler.SensitiveWordHandler;
import cloud.cstream.chat.core.mapper.UserFeedbackDetailsMapper;
import cloud.cstream.chat.core.mapper.UserFeedbackGroupMapper;
import cloud.cstream.chat.core.service.UserFeedbackDetailsService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Service
public class UserFeedbackDetailsServiceImpl extends ServiceImpl<UserFeedbackDetailsMapper, UserFeedbackDetailsDO> implements UserFeedbackDetailsService {

    @Autowired
    private UserFeedbackGroupMapper feedbackGroupMapper;

    @Override
    public void userReply(UserFeedbackSubmitRequest request, Integer userId) {
        UserFeedbackGroupDO feedbackGroup = feedbackGroupMapper.selectById(request.getGroupId());
        if (Objects.isNull(feedbackGroup)){
            throw new ServiceException("无效反馈");
        }
        if (BoolEnum.isTrue(feedbackGroup.getResolved())){
            throw new ServiceException("该反馈已结束，如有问题，请重新提交");
        }
        List<String> hitEl = SensitiveWordHandler.checkWord(request.getContent());
        Assert.isTrue(CollUtil.isEmpty(hitEl), () -> new ServiceException("内容含有敏感词，请修改后提交"));
        UserFeedbackDetailsDO detail = UserFeedbackDetailsDO.builder()
                .groupId(request.getGroupId())
                .uid(userId)
                .role(SysTypeEnum.CLIENT.getCode())
                .content(request.getContent())
                .build();
        baseMapper.insert(detail);
    }

    @Override
    public List<UserFeedbackDetailsPageVO> queryDetailsList(Integer gid, Integer userId) {
        // 全部已读
        baseMapper.readAll(gid, SysTypeEnum.SYS.getCode());
        return baseMapper.queryDetailsList(gid, userId);
    }

    @Override
    public Integer queryUnReadMsgCount(Integer userId) {
        return baseMapper.selectUnreadCount(userId);
    }

    @Override
    public List<UserFeedbackDetailsPageVO> adminFeedbackDetails(Integer groupId) {
        baseMapper.readAll(groupId, SysTypeEnum.CLIENT.getCode());
        return baseMapper.queryDetailsList(groupId, null);
    }

    @Override
    public void adminReplyUser(UserFeedbackSubmitRequest request, Integer sysUserId) {
        UserFeedbackGroupDO feedbackGroupDO = feedbackGroupMapper.selectById(request.getGroupId());
        if (Objects.isNull(feedbackGroupDO)){
            throw new ServiceException("反馈不存在");
        }
        UserFeedbackDetailsDO detail = UserFeedbackDetailsDO.builder()
                .groupId(request.getGroupId())
                .uid(feedbackGroupDO.getUid())
                .role(SysTypeEnum.SYS.getCode())
                .content(request.getContent())
                .build();
        baseMapper.insert(detail);
    }
}

package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.SysTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.UserFeedbackDetailsDO;
import cloud.cstream.chat.core.domain.entity.UserFeedbackGroupDO;
import cloud.cstream.chat.core.domain.query.FeedbackPageQuery;
import cloud.cstream.chat.core.domain.query.PageQuery;
import cloud.cstream.chat.core.domain.request.UserFeedbackSubmitRequest;
import cloud.cstream.chat.core.domain.vo.UserFeedbackAdminGroupPageVO;
import cloud.cstream.chat.core.domain.vo.UserFeedbackGroupPageVO;
import cloud.cstream.chat.core.handler.SensitiveWordHandler;
import cloud.cstream.chat.core.mapper.UserFeedbackDetailsMapper;
import cloud.cstream.chat.core.mapper.UserFeedbackGroupMapper;
import cloud.cstream.chat.core.service.UserFeedbackGroupService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/7/17
 */
@Service
public class UserFeedbackGroupServiceImpl extends ServiceImpl<UserFeedbackGroupMapper, UserFeedbackGroupDO> implements UserFeedbackGroupService {

    @Autowired
    private UserFeedbackDetailsMapper feedbackDetailsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(UserFeedbackSubmitRequest request, Integer userId) {
        this.checkWord(request);
        UserFeedbackGroupDO feedbackGroupDO = UserFeedbackGroupDO.builder()
                .uid(userId)
                .title(request.getTitle())
                .build();
        int insert = baseMapper.insert(feedbackGroupDO);
        if (insert == NumConstant.INT_ZERO){
            throw new ServiceException("提交失败");
        }
        UserFeedbackDetailsDO detail = UserFeedbackDetailsDO.builder()
                .groupId(feedbackGroupDO.getId())
                .uid(userId)
                .role(SysTypeEnum.CLIENT.getCode())
                .content(request.getContent())
                .build();
        feedbackDetailsMapper.insert(detail);
    }

    private void checkWord(UserFeedbackSubmitRequest request) {
        if (CharSequenceUtil.isNotBlank(request.getTitle())){
            List<String> titleHit = SensitiveWordHandler.checkWord(request.getTitle());
            Assert.isTrue(CollUtil.isEmpty(titleHit), "标题含有敏感词！禁止提交！");
        }
        if (CharSequenceUtil.isNotBlank(request.getContent())){
            List<String> contentHit = SensitiveWordHandler.checkWord(request.getContent());
            Assert.isTrue(CollUtil.isEmpty(contentHit), "反馈内容含有敏感词！禁止提交！");
        }
    }

    @Override
    public Page<UserFeedbackGroupPageVO> queryGroupPage(PageQuery query, Integer userId) {
        return baseMapper.queryGroupPage(query.toPage(), userId);
    }


    @Override
    public Page<UserFeedbackAdminGroupPageVO> userFeedbackPage(FeedbackPageQuery query) {
        return baseMapper.userFeedbackPage(query.toPage(), query);
    }

    @Override
    public void resolved(Integer gId) {
        UserFeedbackGroupDO feedbackGroup = baseMapper.selectById(gId);
        if (Objects.isNull(feedbackGroup)){
            throw new ServiceException("反馈不存在");
        }
        if (BoolEnum.isTrue(feedbackGroup.getResolved())){
            return;
        }
        feedbackGroup.setResolved(BoolEnum.TRUE.getVar());
        baseMapper.updateById(feedbackGroup);
    }

}

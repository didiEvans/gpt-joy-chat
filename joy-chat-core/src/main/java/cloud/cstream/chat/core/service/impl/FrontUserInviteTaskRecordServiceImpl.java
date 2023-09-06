package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.InviteTaskStageEnum;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.FrontUserInviteTaskRecordDO;
import cloud.cstream.chat.core.domain.query.InviteePageQuery;
import cloud.cstream.chat.core.domain.vo.UserInviteeListVO;
import cloud.cstream.chat.core.mapper.ClientUserInfoMapper;
import cloud.cstream.chat.core.mapper.FrontUserInviteTaskRecordMapper;
import cloud.cstream.chat.core.service.FrontUserInviteTaskRecordService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author evans
 */
@Slf4j
@Service
public class FrontUserInviteTaskRecordServiceImpl extends ServiceImpl<FrontUserInviteTaskRecordMapper, FrontUserInviteTaskRecordDO> implements FrontUserInviteTaskRecordService {


    @Autowired
    private ClientUserInfoMapper frontUserBaseMapper;

    @Override
    public void initInviteRecords(Integer inviterUid, Integer inviteeUid) {
        if (Objects.isNull(inviterUid)){
            // 如果没有邀请人, 则终止
            return;
        }
        List<FrontUserInviteTaskRecordDO> records = this.build(inviterUid, inviteeUid);
        for (FrontUserInviteTaskRecordDO recordDO : records) {
            try {
                baseMapper.insert(recordDO);
            } catch (DuplicateKeyException dk){
                log.warn("db 中已存在该邀请关系任务:", dk);
            } catch (Exception ex){
                log.error("邀请任务插入过程异常:", ex);
            }
        }
    }

    private List<FrontUserInviteTaskRecordDO> build(Integer inviterUid, Integer inviteeUid) {
        return Arrays.stream(InviteTaskStageEnum.values())
                .map(inviteTaskStageEnum -> new FrontUserInviteTaskRecordDO(inviteeUid, inviterUid, inviteTaskStageEnum.getCode()))
                .toList();
    }

    @Override
    public void tryCompleteTaskStage(InviteTaskStageEnum stageEnum, Integer inviterUid, Integer inviteeUid) {
        if (Objects.isNull(inviterUid)){
            return;
        }
        ClientUserInfoDO userInfo = frontUserBaseMapper.selectById(inviterUid);
        if (Objects.isNull(userInfo)){
            return;
        }
        this.doComplete(stageEnum, inviterUid, inviteeUid, userInfo);
    }

    private void doComplete(InviteTaskStageEnum stageEnum, Integer inviterUid, Integer inviteeUid, ClientUserInfoDO userInfo) {
        FrontUserInviteTaskRecordDO recordDO = baseMapper.selectOne(Wrappers.<FrontUserInviteTaskRecordDO>lambdaQuery()
                .eq(FrontUserInviteTaskRecordDO::getInviterUid, inviterUid)
                .eq(FrontUserInviteTaskRecordDO::getInviteeUid, inviteeUid)
                .eq(FrontUserInviteTaskRecordDO::getTaskCode, stageEnum.getCode()));
        if (Objects.isNull(recordDO)){
            return;
        }
        if (Boolean.TRUE.equals(recordDO.getCompleted())){
            return;
        }
        // 增加邀请人对话次数
        userInfo.setChatCount(NumberUtil.add(userInfo.getChatCount(), stageEnum.getRewardCount()).intValue());
        if (frontUserBaseMapper.updateById(userInfo) > 0){
            recordDO.setReward(stageEnum.getRewardCount());
            recordDO.setCompleted(BoolEnum.TRUE.getMap());
            recordDO.setCompleteTime(DateUtil.date());
            baseMapper.updateById(recordDO);
        }
    }
    @Override
    public void tryCompleteTaskStage(InviteTaskStageEnum inviteTaskStageEnum, Integer inviteeUid) {
        ClientUserInfoDO userInfo = frontUserBaseMapper.selectById(inviteeUid);
        if (Objects.isNull(userInfo)){
            return;
        }
        if (Objects.isNull(userInfo.getInviteUid())){
            return;
        }
        ClientUserInfoDO inviterUserInfo = frontUserBaseMapper.selectById(userInfo.getInviteUid());
        this.doComplete(inviteTaskStageEnum, userInfo.getInviteUid(), inviteeUid, inviterUserInfo);
    }

    @Override
    public Integer selectInviteUserCount(Integer baseUserId) {
        return baseMapper.selectInviteUserCount(baseUserId);
    }

    @Override
    public Page<UserInviteeListVO> queryInviteeList(InviteePageQuery pageQuery) {
        return baseMapper.queryInviteeList(pageQuery.toPage(), pageQuery);
    }
}

package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.core.config.ExtendGlobalConfig;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.query.FrontUserPageQuery;
import cloud.cstream.chat.core.domain.vo.ClientUserPageVO;
import cloud.cstream.chat.core.domain.vo.InviterInfoVO;
import cloud.cstream.chat.core.mapper.ClientUserInfoMapper;
import cloud.cstream.chat.core.service.ClientUserInfoService;
import cloud.cstream.chat.core.service.FrontUserInviteTaskRecordService;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 基础用户服务实现类
 *
 * @author CoDeleven
 */
@Service
public class ClientUserInfoServiceImpl extends ServiceImpl<ClientUserInfoMapper, ClientUserInfoDO> implements ClientUserInfoService {

    @Autowired
    private ExtendGlobalConfig globalConfig;
    @Autowired
    private FrontUserInviteTaskRecordService frontUserInviteTaskRecordService;


    @Override
    public ClientUserInfoDO initializeClientUser(String encryptInviteCode,
                                                 ClientUserRegisterTypeEnum registerTypeEnum,
                                                 String identity,
                                                 String pwd) {
        ClientUserInfoDO userBaseDO = new ClientUserInfoDO();
        userBaseDO.setNickname("JoyChat_" + RandomUtil.randomString(6));
        userBaseDO.setLastLoginIp(null);
        userBaseDO.setDescription(null);
        userBaseDO.setPassword(pwd);
        userBaseDO.setInviteCode(RandomUtil.randomString(14));
        userBaseDO.setInviteUid(this.processInviteEvent(encryptInviteCode));
        userBaseDO.setChatCount(CoreConstants.INIT_CHAT_COUNT);
        userBaseDO.setRegistryType(registerTypeEnum);
        if (registerTypeEnum.equals(ClientUserRegisterTypeEnum.EMAIL)){
            userBaseDO.setEmailAddress(identity);
        } else if(registerTypeEnum.equals(ClientUserRegisterTypeEnum.PHONE)){
            userBaseDO.setMobilePhone(identity);
        }
        this.save(userBaseDO);
        return userBaseDO;
    }

    /**
     * 解析邀请码并且获取邀请人id
     *
     * @param inviteCode 邀请码
     * @return 邀请人id, 为null时无邀请人或邀请码解析失败
     */
    private Integer processInviteEvent(String inviteCode) {
        if (CharSequenceUtil.isBlankOrUndefined(inviteCode)){
            return null;
        }
        ClientUserInfoDO userBaseDO = baseMapper.selectOne(Wrappers.<ClientUserInfoDO>lambdaQuery().eq(ClientUserInfoDO::getInviteCode, inviteCode).last(CoreConstants.LIMIT_1));
        if (Objects.isNull(userBaseDO)){
            return null;
        }
        return userBaseDO.getId();
    }

    @Override
    public ClientUserInfoDO findUserInfoById(Integer baseUserId) {
        return getById(baseUserId);
    }

    @Override
    public void updateLastLoginIp(Integer baseUserId) {
        update(new ClientUserInfoDO(), new LambdaUpdateWrapper<ClientUserInfoDO>()
                .set(ClientUserInfoDO::getLastLoginIp, WebUtil.getIp())
                .eq(ClientUserInfoDO::getId, baseUserId));
    }

    @Override
    public Page<ClientUserPageVO> pageFrontUserInfo(FrontUserPageQuery pageQuery) {
        return baseMapper.queryPage(pageQuery.toPage(), pageQuery);
    }

    @Override
    public InviterInfoVO getInviterInfo(Integer userId) {
        ClientUserInfoDO userInfo = Optional.ofNullable(this.findUserInfoById(userId)).orElseThrow(() -> new ServiceException("非法访问"));
        String inviteUrl = StrUtil.format(globalConfig.getBaseWebUrl(), userInfo.getInviteCode());
        return InviterInfoVO.builder().inviteCode(userInfo.getInviteCode()).inviteUrl(inviteUrl).build();
    }

    @Override
    public List<String> getRechargeQrCode() {
        return globalConfig.getQrCodeUrls();
    }

    @Override
    public Integer getCurrentUserInviteUserCount(Integer baseUserId) {
        return frontUserInviteTaskRecordService.selectInviteUserCount(baseUserId);
    }


    @Override
    public ClientUserInfoDO getClientUserInfoByEmailAccount(String email) {
        return baseMapper.queryClientUserInfoByEmailAccount(email);
    }

    @Override
    public ClientUserInfoDO getClientUserInfoByIdentification(String username) {
        return baseMapper.queryClientUserInfoByIdentification(username);
    }

    @Override
    public ClientUserInfoDO getClientUserInfoByMobilePhone(String identity) {
        return baseMapper.queryClientUserInfoByMobilePhone(identity);
    }

    @Override
    public void clientUserEnableSwitch(Integer userId) {
        ClientUserInfoDO userInfo = this.findUserInfoById(userId);
        if (Objects.isNull(userInfo)){
            return;
        }
        Integer currStatus = BoolEnum.isTrue(userInfo.getStatus())? BoolEnum.FALSE.getVar(): BoolEnum.TRUE.getVar();
        userInfo.setStatus(currStatus);
        this.updateById(userInfo);
    }
}

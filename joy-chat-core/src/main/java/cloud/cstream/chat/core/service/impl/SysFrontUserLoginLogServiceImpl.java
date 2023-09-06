package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.core.domain.entity.SysFrontUserLoginLogDO;
import cloud.cstream.chat.core.mapper.SysFrontUserLoginLogMapper;
import cloud.cstream.chat.core.service.ClientUserInfoService;
import cloud.cstream.chat.core.service.SysFrontUserLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前端用户登录日志业务实现类
 *
 * @author CoDeleven
 */
@Service
public class SysFrontUserLoginLogServiceImpl extends ServiceImpl<SysFrontUserLoginLogMapper, SysFrontUserLoginLogDO> implements SysFrontUserLoginLogService {

    @Resource
    private ClientUserInfoService frontUserBaseService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void loginFailed(ClientUserRegisterTypeEnum registerType, Integer baseUserId, String message) {
        SysFrontUserLoginLogDO logDO = new SysFrontUserLoginLogDO();
        logDO.setBaseUserId(baseUserId);
        logDO.setLoginStatus(false);
        logDO.setLoginType(registerType);
        logDO.setMessage(message);
        logDO.setLoginIp(WebUtil.getIp());
        this.save(logDO);
    }

    @Override
    public void loginSuccess(ClientUserRegisterTypeEnum registerType, Integer extraInfoId, Integer baseUserId) {
        SysFrontUserLoginLogDO logDO = new SysFrontUserLoginLogDO();
        logDO.setBaseUserId(baseUserId);
        logDO.setLoginExtraInfoId(extraInfoId);
        logDO.setLoginStatus(true);
        logDO.setLoginType(registerType);
        logDO.setMessage("success");
        logDO.setLoginIp(WebUtil.getIp());
        this.save(logDO);

        // 更新上次登录时间
        frontUserBaseService.updateLastLoginIp(baseUserId);
    }
}





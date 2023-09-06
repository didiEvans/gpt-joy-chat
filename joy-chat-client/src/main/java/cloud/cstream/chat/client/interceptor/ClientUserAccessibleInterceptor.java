package cloud.cstream.chat.client.interceptor;

import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.exception.AuthException;
import cloud.cstream.chat.common.exception.IllegalApiAccessException;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.config.SaTokenConfig;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.pojo.ShutdownAppConf;
import cloud.cstream.chat.core.service.ClientUserInfoService;
import cloud.cstream.chat.core.service.SysParamService;
import cn.hutool.core.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * 可访问
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Slf4j
@Component
public class ClientUserAccessibleInterceptor implements HandlerInterceptor {

    @Autowired
    private ClientUserInfoService clientUserInfoService;
    @Autowired
    private SysParamService sysParamService;


    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        this.appIsUpgrading();
        String requestUri = request.getRequestURI();
        if (SaTokenConfig.EXCLUDE_API.contains(requestUri)) {
            return true;
        }
        // 获取用户id
        Integer userId = ClientUserLoginUtil.getUserId();
        ClientUserInfoDO userInfo = clientUserInfoService.findUserInfoById(userId);
        if (Objects.isNull(userInfo)) {
            throw new IllegalApiAccessException("非法访问");
        }
        if (BoolEnum.isTrue(userInfo.getStatus())) {
            throw new AuthException("账号已被禁用,禁止访问本站");
        }
        return true;
    }

    private void appIsUpgrading() {
        ShutdownAppConf upgradeConfValue = sysParamService.getUpgradeConfValue();
        if (Objects.nonNull(upgradeConfValue)) {
            Assert.isTrue(BoolEnum.isFalse(upgradeConfValue.getEnableSwitch()), () -> new ServiceException(upgradeConfValue.getAnnounce()));
        }
    }

}

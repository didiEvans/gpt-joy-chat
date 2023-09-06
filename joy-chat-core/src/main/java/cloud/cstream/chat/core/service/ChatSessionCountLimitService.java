package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.VasPkgDO;

/**
 * @author evans
 * @description
 * @date 2023/5/9
 */
public interface ChatSessionCountLimitService {
    /**
     * 是否到达每日对话上限
     *
     * @param userInfo 用户信息
     */
    void isReachTheFreeLimit(ClientUserInfoDO userInfo);

    /**
     * 是否到达配置上限
     *
     * @param currentVasPkg
     */
    void checkReachTheConfLimit(VasPkgDO currentVasPkg);
}

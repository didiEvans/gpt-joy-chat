package cloud.cstream.chat.client.utils;

import cloud.cstream.chat.client.domain.bo.JwtUserInfoBO;
import cloud.cstream.chat.common.constants.ApplicationConstant;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.experimental.UtilityClass;

/**
 * @author Anker
 * @date 2023/4/16 17:23
 * 前端用户工具类
 */
@UtilityClass
public class ClientUserLoginUtil {

    /**
     * 获取用户 id
     *
     * @return 用户 id
     */
    public Integer getUserId() {
        return NumberUtil.parseInt(String.valueOf(StpUtil.getLoginId()));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public JwtUserInfoBO getJwtUserInfo() {
        JwtUserInfoBO userInfoBO = new JwtUserInfoBO();
        String registerTypeCode = (String) StpUtil.getExtra(ApplicationConstant.FRONT_JWT_REGISTER_TYPE_CODE);
        ClientUserRegisterTypeEnum registerType = ClientUserRegisterTypeEnum.CODE_MAP.get(registerTypeCode);
        userInfoBO.setRegisterType(registerType);
        userInfoBO.setUsername(String.valueOf(StpUtil.getExtra(ApplicationConstant.FRONT_JWT_USERNAME)));
        userInfoBO.setUserId(getUserId());
        userInfoBO.setExtraUserId(NumberUtil.parseInt(String.valueOf(StpUtil.getExtra(ApplicationConstant.FRONT_JWT_EXTRA_USER_ID))));
        return userInfoBO;
    }
}

package cloud.cstream.chat.client.service.strategy.user;

import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.domain.vo.LoginInfoVO;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.EncryptUtil;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注册类型策略抽象类
 *
 * @author CoDeleven
 */
public abstract class AbstractRegisterTypeStrategy {

    @Autowired
    private RedisClient redisClient;
    /**
     * 根据注册类型获取逻辑处理策略
     *
     * @param registerType 注册类型
     * @return 策略
     */
    public static AbstractRegisterTypeStrategy findStrategyByRegisterType(ClientUserRegisterTypeEnum registerType) {
        switch (registerType) {
            // 邮箱注册策略
            case EMAIL -> {
                return SpringUtil.getBean(EmailAbstractRegisterStrategy.class);
            }
            case PHONE -> {
                return SpringUtil.getBean(PhoneAbstractRegisterStrategy.class);
            }
            default -> {
                break;
            }
        }
        throw new ServiceException(StrUtil.format("暂不支持{}注册逻辑", registerType.getDesc()));
    }

    /**
     * 给原生密码+盐进行加密
     *
     * @return 返回加密后16进制的字符串
     */
    protected String encryptRawPassword(String rawPassword, String salt) {
        return EncryptUtil.md5Encrypt(rawPassword, salt);
    }

    /**
     * 验证是否是有效的注册载体
     *
     * @param identity 邮箱注册就是邮箱，手机号注册就是手机
     * @return true有效，false无效
     */
    public abstract boolean identityUsed(String identity);

    /**
     * 执行注册逻辑
     *
     * @param request 注册请求
     */
    public abstract void register(RegisterFrontUserRequest request);

    /**
     * 校验验证码是否通过
     *
     * @param identity          用户账号，可能为空。一般邮箱情况下会为空，手机情况下不为空
     * @param verifyCode        邮箱策略时为邮箱验证码；手机策略时为手机短信验证码
     * @param encryptInviteCode 加密邀请码
     */
    public abstract void checkVerifyCode(String identity, String verifyCode, String encryptInviteCode);

    /**
     * 登录
     *
     * @param username   用户名，可以是手机号、邮箱
     * @param password   短信验证码/密码
     * @param loginType
     * @param inviteCode
     * @return 登录成功后的信息
     */
    public abstract LoginInfoVO login(String username, String password, Integer loginType, String captcha, String inviteCode);

}
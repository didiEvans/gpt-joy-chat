package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.ClientUserVerifyInfoDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 使用邮箱登录的前端用户服务
 *
 * @author CoDeleven
 */
public interface ClientUserVerifyInfoService extends IService<ClientUserVerifyInfoDO> {

    /**
     * 是否已使用
     *
     * @param identity   邮箱/手机号
     * @return 是否已使用，true已使用；false未使用
     */
    boolean isUsed(String identity);

    /**
     * 获取一个未被验证的邮箱账号信息
     *
         * @param uid 用户id
     * @return 邮箱信息
     */
    ClientUserVerifyInfoDO getUserVerifiedInfo(Integer uid);

    /**
     * 邮件验证完毕
     *
     * @param emailExtraInfo 邮箱登录信息
     */
    void verifySuccess(ClientUserVerifyInfoDO emailExtraInfo);

    /**
     * 查询用户验证信息
     *
     * @param uid 用户id
     * @return
     */
    ClientUserVerifyInfoDO queryUserVerifyInfo(Integer uid);
}

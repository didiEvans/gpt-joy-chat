package cloud.cstream.chat.core.service;

import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.query.FrontUserPageQuery;
import cloud.cstream.chat.core.domain.vo.ClientUserPageVO;
import cloud.cstream.chat.core.domain.vo.InviterInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 前端用户基础用户业务接口
 *
 * @author CoDeleven
 */
public interface ClientUserInfoService extends IService<ClientUserInfoDO> {

    /**
     * 创建一个空的基础用户信息
     *
     * @return 用户信息
     */
    ClientUserInfoDO initializeClientUser(String encryptInviteCode, ClientUserRegisterTypeEnum registerTypeEnum, String identity, String pwd);

    /**
     * 获取基础用户信息
     *
     * @param baseUserId 基础用户 id
     * @return 用户信息
     */
    ClientUserInfoDO findUserInfoById(Integer baseUserId);

    /**
     * 更新上次登录 ip
     *
     * @param baseUserId 基础用户 id
     */
    void updateLastLoginIp(Integer baseUserId);

    /**
     * 分页查询用户信息
     *
     * @param pageQuery 分页查询参数
     * @return {@link Page< ClientUserPageVO >}
     */
    Page<ClientUserPageVO> pageFrontUserInfo(FrontUserPageQuery pageQuery);

    /**
     * 获取邀请信息
     *
     * @param userId 用户id
     * @return
     */
    InviterInfoVO getInviterInfo(Integer userId);

    /**
     * 获取充值二维码
     *
     * @return
     */
    List<String> getRechargeQrCode();

    /**
     * 查询当前用户邀请用户数
     *
     * @param baseUserId
     * @return
     */
    Integer getCurrentUserInviteUserCount(Integer baseUserId);

    /**
     * 使用邮箱获取用户信息
     *
     * @param email 邮箱地址
     * @return {@link ClientUserInfoDO}
     */
    ClientUserInfoDO getClientUserInfoByEmailAccount(String email);

    /**
     * 标识符
     *
     * @param username
     * @return
     */
    ClientUserInfoDO getClientUserInfoByIdentification(String username);

    /**
     * 使用手机号和获取用户信息
     *
     * @param identity
     * @return
     */
    ClientUserInfoDO getClientUserInfoByMobilePhone(String identity);

    void clientUserEnableSwitch(Integer userId);
}

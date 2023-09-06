package cloud.cstream.chat.client.domain.vo;

import cloud.cstream.chat.core.domain.vo.VasPkgVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功后返回前端用户信息
 *
 * @author CoDeleven
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserInfoVO {
    /**
     * 基础用户ID
     */
    private Integer baseUserId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobilePhone;
    /**
     * 自我介绍
     */
    private String description;
    /**
     * 头像地址
     */
    private String avatarVersion;
    /**
     * 当前增值 套餐
     */
    private VasPkgVO currentVas;
    /**
     * 总会话次数
     */
    private Integer totalSessionCount;
    /**
     * 剩余会话次数
     */
    private Integer residualSessionCount;
    /**
     * 额外会话次数
     */
    private Integer extraSessionCount;
    /**
     * 邀请用户人数
     */
    private Integer inviterUserCount;
    /**
     * 积分
     */
    private Integer points;

}

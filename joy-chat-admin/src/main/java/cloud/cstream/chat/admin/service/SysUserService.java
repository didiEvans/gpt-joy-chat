package cloud.cstream.chat.admin.service;


import cloud.cstream.chat.core.domain.entity.SysUserDO;
import cloud.cstream.chat.core.domain.request.SysUserLoginRequest;
import cloud.cstream.chat.core.domain.vo.SysUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Anker
 * @date 2023/3/28 12:43
 * 系统用户相关接口
 */
public interface SysUserService extends IService<SysUserDO> {

    /**
     * 登录
     *
     * @param sysUserLoginRequest 登录参数
     */
    SysUserVO login(SysUserLoginRequest sysUserLoginRequest);
}

package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.core.domain.request.SysUserLoginRequest;
import cloud.cstream.chat.admin.service.SysUserService;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.domain.vo.SysUserVO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin-系统用户相关接口
 *
 * @author Anker
 * @date 2023/3/28 09:54
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/sys_user")
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 用户登录
     * @param sysUserLoginRequest
     * @return
     */
    @PostMapping("/login")
    public R<SysUserVO> login(@Validated @RequestBody SysUserLoginRequest sysUserLoginRequest) {
        return R.data(sysUserService.login(sysUserLoginRequest));
    }
}

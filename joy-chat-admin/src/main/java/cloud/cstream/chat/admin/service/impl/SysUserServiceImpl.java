package cloud.cstream.chat.admin.service.impl;

import cloud.cstream.chat.admin.service.SysUserService;
import cloud.cstream.chat.common.exception.AuthException;
import cloud.cstream.chat.common.util.StpAdminUtil;
import cloud.cstream.chat.core.domain.entity.SysUserDO;
import cloud.cstream.chat.core.domain.request.SysUserLoginRequest;
import cloud.cstream.chat.core.domain.vo.SysUserVO;
import cloud.cstream.chat.core.mapper.SysUserMapper;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author Anker
 * @date 2023/3/28 12:44
 * 系统用户业务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {


    @Override
    public SysUserVO login(SysUserLoginRequest request) {
        SysUserDO sysUser = baseMapper.selectOne(Wrappers.<SysUserDO>lambdaQuery()
                .eq(SysUserDO::getUserName, request.getAccount())
                .eq(SysUserDO::getPassword, MD5.create().digestHex(request.getPassword(), Charset.defaultCharset())));
        if (Objects.isNull(sysUser)){
            throw new AuthException("账号或密码错误");
        }
        StpAdminUtil.login(sysUser.getId());
        return SysUserVO.builder()
                .nickName(sysUser.getNickName())
                .avatarUrl(sysUser.getAvatar())
                .username(sysUser.getUserName()).build();
    }
}

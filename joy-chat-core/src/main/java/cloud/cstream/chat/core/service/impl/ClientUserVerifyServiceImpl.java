package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.util.ThrowExceptionUtil;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.ClientUserVerifyInfoDO;
import cloud.cstream.chat.core.mapper.ClientUserVerifyInfoMapper;
import cloud.cstream.chat.core.service.ClientUserInfoService;
import cloud.cstream.chat.core.service.ClientUserVerifyInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 通用的前端用户邮箱登录方式服务
 *
 * @author CoDeleven
 */
@Service
public class ClientUserVerifyServiceImpl extends ServiceImpl<ClientUserVerifyInfoMapper, ClientUserVerifyInfoDO> implements ClientUserVerifyInfoService {

    @Autowired
    private ClientUserInfoService userInfoService;
    @Override
    public boolean isUsed(String identity) {
        ClientUserInfoDO userInfo = userInfoService.getClientUserInfoByIdentification(identity);
        if (Objects.isNull(userInfo)) {
            return false;
        }
        return  this.getOne(Wrappers.<ClientUserVerifyInfoDO>lambdaQuery().eq(ClientUserVerifyInfoDO::getUid, userInfo.getId())).getVerified();
    }

    @Override
    public ClientUserVerifyInfoDO getUserVerifiedInfo(Integer uid) {
        return this.getOne(new LambdaQueryWrapper<ClientUserVerifyInfoDO>()
                .eq(ClientUserVerifyInfoDO::getUid, uid));
    }


    @Override
    public void verifySuccess(ClientUserVerifyInfoDO verifyInfoDO) {
        if (verifyInfoDO.getVerified()){
            return;
        }
        ThrowExceptionUtil.isFalse(update(new ClientUserVerifyInfoDO(), new LambdaUpdateWrapper<ClientUserVerifyInfoDO>()
                        .set(ClientUserVerifyInfoDO::getVerified, true)
                        .eq(ClientUserVerifyInfoDO::getVerified, false)
                        .eq(ClientUserVerifyInfoDO::getId, verifyInfoDO.getId())))
                .throwMessage("邮箱验证码失败");
    }

    @Override
    public ClientUserVerifyInfoDO queryUserVerifyInfo(Integer uid) {
        return this.getOne(Wrappers.<ClientUserVerifyInfoDO>lambdaQuery().eq(ClientUserVerifyInfoDO::getUid, uid));
    }
}
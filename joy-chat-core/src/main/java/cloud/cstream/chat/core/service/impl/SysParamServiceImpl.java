package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.RedisKeyConstant;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.core.domain.entity.SysParamDO;
import cloud.cstream.chat.core.domain.pojo.ShutdownAppConf;
import cloud.cstream.chat.core.mapper.SysParamMapper;
import cloud.cstream.chat.core.service.SysParamService;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static cloud.cstream.chat.common.constants.SysParamConfKeys.APP_UPGRADE_CONF_KEY;

@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParamDO> implements SysParamService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public void enableSwitchUpdate(ShutdownAppConf request) {
        redisClient.delete(RedisKeyConstant.APP_UPGRADE_CONF);
        SysParamDO sysParamDO = baseMapper.selectOne(Wrappers.<SysParamDO>lambdaQuery().eq(SysParamDO::getConfKey, APP_UPGRADE_CONF_KEY));
        if (Objects.isNull(sysParamDO)) {
            sysParamDO = SysParamDO.builder().confKey(APP_UPGRADE_CONF_KEY).confValue(JSONUtil.toJsonStr(request)).build();
            baseMapper.insert(sysParamDO);
        } else {
            sysParamDO.setConfValue(JSONUtil.toJsonStr(request));
            baseMapper.updateById(sysParamDO);
        }
        redisClient.delete(RedisKeyConstant.APP_UPGRADE_CONF);
    }

    @Override
    public ShutdownAppConf getUpgradeConfValue() {
        ShutdownAppConf appConf = redisClient.get(RedisKeyConstant.APP_UPGRADE_CONF, ShutdownAppConf.class);
        if (Objects.nonNull(appConf)) {
            return appConf;
        }
        SysParamDO sysParamDO = baseMapper.selectOne(Wrappers.<SysParamDO>lambdaQuery().eq(SysParamDO::getConfKey, APP_UPGRADE_CONF_KEY));
        if (Objects.nonNull(sysParamDO)) {
            return JSONUtil.toBean(sysParamDO.getConfValue(), ShutdownAppConf.class);
        } else {
            appConf = ShutdownAppConf.builder().enableSwitch(BoolEnum.FALSE.getVar()).announce("无更新").build();
            sysParamDO = SysParamDO.builder().confKey(APP_UPGRADE_CONF_KEY).confValue(JSONUtil.toJsonStr(appConf)).build();
            baseMapper.insert(sysParamDO);
        }
        redisClient.set(RedisKeyConstant.APP_UPGRADE_CONF, JSONUtil.toJsonStr(appConf));
        return appConf;
    }
}

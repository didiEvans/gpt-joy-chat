package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.SysParamDO;
import cloud.cstream.chat.core.domain.pojo.ShutdownAppConf;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysParamService extends IService<SysParamDO> {
    void enableSwitchUpdate(ShutdownAppConf request);

    ShutdownAppConf getUpgradeConfValue();

}

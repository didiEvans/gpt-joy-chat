package cloud.cstream.chat.admin.controller;


import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.domain.pojo.ShutdownAppConf;
import cloud.cstream.chat.core.service.SysParamService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * admin-应用升级配置
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/dashboard")
public class AdminDashboardController {


    @Autowired
    private SysParamService sysParamService;

    /**
     * 更新配置
     *
     * @param request
     * @return
     */
    @PostMapping("/shutdown_app_conf")
    public R<Void> enableSwitchUpdate(@RequestBody @Validated ShutdownAppConf request){
        sysParamService.enableSwitchUpdate(request);
        return R.success();
    }

    /**
     * 查询配置
     *
     * @return
     */
    @GetMapping("/get_conf_value")
    public R<ShutdownAppConf> getConfValue(){
        return R.data(sysParamService.getUpgradeConfValue());
    }


}

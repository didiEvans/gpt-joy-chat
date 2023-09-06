package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.common.valid.ValidGroup;
import cloud.cstream.chat.core.domain.query.SysAdConfigQuery;
import cloud.cstream.chat.core.domain.request.SysAdConfigRequest;
import cloud.cstream.chat.core.domain.vo.SysAdConfigVO;
import cloud.cstream.chat.core.service.SysAdConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/ad_config")
public class SysAdConfigController {

    @Autowired
    private SysAdConfigService adConfigService;

    @PostMapping("/pages")
    public R<Page<SysAdConfigVO>> queryPage(@RequestBody SysAdConfigQuery pageQuery){
        return R.data(adConfigService.queryPage(pageQuery));
    }


    @PostMapping("/add")
    public R<Void> addConfig(@RequestBody @Validated(ValidGroup.Add.class) SysAdConfigRequest request){
        adConfigService.add(request);
        return R.success();
    }

    @PostMapping("/update")
    public R<Void> updateConfig(@RequestBody @Validated(ValidGroup.Update.class)  SysAdConfigRequest request){
        adConfigService.updateConfig(request);
        return R.success();
    }

    @PostMapping("/enable_switch")
    public R<Void> enableSwitch(@RequestBody @Validated(ValidGroup.Update.class)  SysAdConfigRequest request){
        adConfigService.enableSwitch(request);
        return R.success();
    }
}

package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.core.domain.request.OpeningVasRequest;
import cloud.cstream.chat.core.domain.vo.ClientUserPageVO;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.domain.query.FrontUserPageQuery;
import cloud.cstream.chat.core.service.ClientUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * admin-客户端用户控制器
 *
 * @author evans
 * @description
 * @date 2023/5/7
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/front_user")
public class ClientUserInfoController {

    private final ClientUserInfoService frontUserBaseService;

    /**
     * 客户端用户分页列表
     *
     * @param pageQuery  分页查询参数
     * @return {@link R<IPage< ClientUserPageVO >>}
     */
    @PostMapping("/page")
    public R<IPage<ClientUserPageVO>> page(@Validated @RequestBody FrontUserPageQuery pageQuery) {
        return R.data(frontUserBaseService.pageFrontUserInfo(pageQuery));
    }

    /**
     * 手动开通套餐
     *
     * @param request
     * @return
     */
    @PostMapping("/enable_switch")
    public R<Void> clientUserEnableSwitch(@RequestParam("userId") Integer userId){
        frontUserBaseService.clientUserEnableSwitch(userId);
        return R.success();
    }


}

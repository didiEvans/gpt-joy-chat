package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.core.domain.vo.RateLimitVO;
import cloud.cstream.chat.admin.service.RateLimitService;
import cloud.cstream.chat.common.domain.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * admin-限流记录相关接口
 *
 * @author Anker
 * @date 2023/4/1 04:48
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/rate_limit")
public class RateLimitController {

    private final RateLimitService rateLimitService;

    /**
     * 限流列表
     * @return
     */
    @GetMapping("/list")
    public R<List<RateLimitVO>> listRateLimit() {
        return R.data(rateLimitService.listRateLimit());
    }
}

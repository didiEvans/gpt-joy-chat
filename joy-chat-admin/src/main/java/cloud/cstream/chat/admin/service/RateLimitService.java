package cloud.cstream.chat.admin.service;


import cloud.cstream.chat.core.domain.vo.RateLimitVO;

import java.util.List;

/**
 * @author Anker
 * @date 2023/4/1 04:52
 * 限流记录业务接口
 */
public interface RateLimitService {

    /**
     * 查询限流列表
     *
     * @return 限流列表
     */
    List<RateLimitVO> listRateLimit();
}

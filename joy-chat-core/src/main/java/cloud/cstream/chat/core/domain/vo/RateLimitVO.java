package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

/**
 * @author Anker
 * @date 2023/3/30 18:10
 * 限流处展示参数
 */
@Data
public class RateLimitVO {
    /**
     * ip
     */
    private String ip;

    /**
     * ip 限流规则
     */
    private String ipLimitRule;

    /**
     * 全局限流规则
     */
    private String globalLimitRule;

    /**
     * 是否被 ip 限流
     */
    private Boolean isIpLimited;

    /**
     * 是否被全局限流
     */
    private Boolean isGlobalLimited;
    /**
     * ip 限制时间内已发送次数
     */
    private Integer alreadySendCount;

    /**
     * 下次可以发送消息的时间
     */
    private String nextSendTime;
}

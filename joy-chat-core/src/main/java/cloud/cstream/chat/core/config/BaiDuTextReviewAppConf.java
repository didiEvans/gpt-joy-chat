package cloud.cstream.chat.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 百度文本审核应用
 *
 * @author evans
 * @description
 * @date 2023/5/19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "baidu-content-review")
public class BaiDuTextReviewAppConf {

    /**
     * 应用id
     */
    private String clientId;

    /**
     * 应用密钥
     */
    private String clientSecret;

    /**
     * @see https://aip.baidubce.com/oauth/2.0/token
     */
    private String accessTokenUrl;

    /**
     * @see https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined
     */
    private String doCheckActionUrl;

}

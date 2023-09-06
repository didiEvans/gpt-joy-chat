package cloud.cstream.chat.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/5/8
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "extend")
public class ExtendGlobalConfig {

    /**
     * 前端访问地址
     */
    private String baseWebUrl;
    /**
     * 充值二维码地址
     */
    private List<String> qrCodeUrls;

}

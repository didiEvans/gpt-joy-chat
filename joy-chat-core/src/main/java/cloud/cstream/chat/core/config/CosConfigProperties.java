package cloud.cstream.chat.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cos")
public class CosConfigProperties {


    private String region;

    private String bucketName;


    private String secretId;

    private String secretKey;

}

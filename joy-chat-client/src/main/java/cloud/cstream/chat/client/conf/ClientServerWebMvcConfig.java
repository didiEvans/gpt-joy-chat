package cloud.cstream.chat.client.conf;

import cloud.cstream.chat.client.interceptor.ClientUserAccessibleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ClientServerWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ClientUserAccessibleInterceptor accessibleInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessibleInterceptor)
                .addPathPatterns("/client/**")
                .order(1);
    }
}

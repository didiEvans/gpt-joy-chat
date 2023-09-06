package cloud.cstream.chat.core.config;

import cloud.cstream.chat.core.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AccessInterceptor accessInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor)
                .addPathPatterns("/**");
    }
}

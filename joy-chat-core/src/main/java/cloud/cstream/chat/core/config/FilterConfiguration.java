package cloud.cstream.chat.core.config;

import cloud.cstream.chat.core.filters.RepeatableReadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<RepeatableReadFilter> repeatableReadFilter() {
        FilterRegistrationBean<RepeatableReadFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableReadFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableReadFilter");
        registration.setOrder(1);
        return registration;
    }
}

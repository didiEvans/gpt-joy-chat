package cloud.cstream.chat.core.config;

import cloud.cstream.chat.common.util.StpAdminUtil;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/28 12:48
 * SaToken 配置，目前针对管理端鉴权
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    public static final List<String> EXCLUDE_API = Lists.newArrayList(
            "/favicon.ico",
            "/admin/sys_user/login",
            "/client/user/verify_email_code",
            "/client/prompt_lib/list",
            "/client/user/register/email",
            "/client/user/get_pic_code",
            "/client/user/login/email",
            "/client/user/send_captcha",
            "/client/user/login/phone",
            "/client/user/Identify_exists",
            "/client/prompt_lib/toolbox"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
                    // 管理端接口都必须管理端登录
                    SaRouter.match("/admin/**").check(r -> StpAdminUtil.checkLogin());
                    // 非管理端接口都必须 front 用户登录
                    SaRouter.match("/client/**").check(r -> StpUtil.checkLogin());
                }))
                .excludePathPatterns(EXCLUDE_API);
    }

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }
}

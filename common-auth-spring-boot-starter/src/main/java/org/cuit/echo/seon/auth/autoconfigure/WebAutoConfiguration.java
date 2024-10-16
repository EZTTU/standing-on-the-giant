package org.cuit.echo.seon.auth.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cuit.echo.seon.commonauth.config.AuthConfig;
import org.cuit.echo.seon.commonauth.filter.AuthFilter;
import org.cuit.echo.seon.commonauth.interceptor.AuthenticationInterceptor;
import org.cuit.echo.seon.commonauth.utils.AuthUtils;
import org.cuit.echo.seon.commonweb.utils.SpringContextUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SeonExlike
 * @date 2022/10/12
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import({AuthConfig.class, AuthFilter.class, AuthUtils.class, AuthenticationInterceptor.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    private final AuthConfig authConfig; // 注入 AuthConfig

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SpringContextUtils.getBean(AuthenticationInterceptor.class))
                .addPathPatterns(authConfig.getPathPattern());
        log.info("认证拦截器注册完毕");
    }
}

package org.cuit.echo.seon.web.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cuit.echo.seon.commonweb.handle.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SeonExlike
 * @date 2022/10/12
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import({GlobalExceptionHandler.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .exposedHeaders("Content-Disposition");
    }
}

package org.cuit.echo.seon.commonauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeonExlike
 * @date 2022/10/12
 */
@Data
@Configuration
@ConfigurationProperties("auth")
public class AuthConfig {

    /**
     * token存入redis中的前缀
     */
    private String tokenPrefix;

    /**
     * token过期时间
     */
    private Duration expireTime = Duration.ofDays(1);

    /**
     * 是否每次请求刷新过期时间
     */
    private Boolean refreshToken = true;

    /**
     * 拦截路径
     */
    private String pathPattern = "/**";

    /**
     * 拦截路径白名单
     */
    private List<String> whiteList = new ArrayList<>();
}

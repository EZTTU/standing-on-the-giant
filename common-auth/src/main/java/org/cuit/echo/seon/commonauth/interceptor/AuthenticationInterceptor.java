package org.cuit.echo.seon.commonauth.interceptor;

import org.cuit.echo.seon.commonauth.AuthInfo;
import org.cuit.echo.seon.commonauth.annotation.RequiresAuth;
import org.cuit.echo.seon.commonauth.config.AuthConfig;
import org.cuit.echo.seon.commonauth.utils.AuthUtils;
import org.cuit.echo.seon.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器，使用白名单模式
 *
 * @author SeonExlike
 * @date 2022/5/11
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String uri = request.getRequestURI();

            if ("/error".equals(uri)) {
                return true;
            }

            RequiresAuth requiresAuth = ((HandlerMethod) handler).getMethodAnnotation(RequiresAuth.class);
            if (requiresAuth != null && !requiresAuth.value()) {
                return true;
            }

            for (String pattern : authConfig.getWhiteList()) {
                if (PATH_MATCHER.match(pattern, uri)) {
                    return true;
                }
            }

            AuthInfo authInfo = AuthUtils.getAuthInfo();
            if (authInfo != null && authConfig.getRefreshToken()) {
                redisTemplate.expire(authInfo.getRedisKey(), authConfig.getExpireTime());
                return true;
            }

            throw new BusinessException("未登录", 401);
        }

        return true;
    }
}

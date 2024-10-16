package org.cuit.echo.seon.commonauth.filter;


import lombok.extern.slf4j.Slf4j;
import org.cuit.echo.seon.commonauth.utils.AuthUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SeonExlike
 * @date 2022/7/22
 */
@Slf4j
@Order(-1000)
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("token");
        AuthUtils.setAuthInfo(token);
        log.debug("当前用户信息：{}, 访问路径: {}", AuthUtils.getUserId(), request.getRequestURI());
        try {
            filterChain.doFilter(request, response);
        } finally {
            AuthUtils.reset();
            log.debug("清除用户信息：{}", AuthUtils.getUserId());
        }
    }
}

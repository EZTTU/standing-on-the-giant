package org.cuit.echo.seon.commonauth.utils;


import org.apache.commons.lang3.StringUtils;
import org.cuit.echo.seon.commonauth.AuthInfo;
import org.cuit.echo.seon.commonauth.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author SeonExlike
 * @date 2022/5/11
 */
@Component
public class AuthUtils {

    private static final String REDIS_SEPARATOR = ":";

    private static final ThreadLocal<AuthInfo> authInfos = new ThreadLocal<>();

    private static RedisTemplate<String, Object> redisTemplate;

    private static AuthConfig authConfig;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        AuthUtils.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setAuthConfig(AuthConfig authConfig) {
        AuthUtils.authConfig = authConfig;
    }

    /**
     * 清空当前认证信息
     */
    public static void reset() {
        authInfos.remove();
    }

    /**
     * 设置当前认证信息
     *
     * @param token 身份令牌
     */
    public static void setAuthInfo(String token) {
        if (StringUtils.isBlank(token)) {
            reset();
            return;
        }

        String redisKey = getRedisKey(tokenToUser(token));

        if (redisKey == null) {
            return;
        }

        Object info = redisTemplate.opsForValue().get(redisKey);
        AuthInfo authInfo = null;
        if (info != null) {
            authInfo = new AuthInfo();
            authInfo.setToken(token);
            authInfo.setRedisKey(redisKey);
            String[] split = redisKey.split(REDIS_SEPARATOR);
            authInfo.setUserId(Integer.parseInt(split[split.length - 1]));
            authInfo.setInfo(info);
        }

        authInfos.set(authInfo);
    }

    /**
     * 获取当前用户信息
     *
     * @return 认证用户信息
     */
    public static AuthInfo getAuthInfo() {
        return authInfos.get();
    }

    /**
     * 将 token 存入 redis 中
     *
     * @param token  token
     * @param userId 用户ID
     * @param value  内容
     */
    public static void setToken(String token, Integer userId, Object value) {
        if (value == null) {
            throw new NullPointerException("value must not be null");
        }
        redisTemplate.opsForValue().set(userToken(token, userId), value, authConfig.getExpireTime());
    }

    /**
     * 删除用户 token
     *
     * @param token  token
     * @param userId 用户ID
     */
    public static void deleteToken(String token, Integer userId) {
        redisTemplate.delete(userToken(token, userId));
    }

    /**
     * 删除用户全部 token
     *
     * @param userId 用户ID
     */
    public static void deleteAllToken(Integer userId) {
        Set<String> keys = redisTemplate.keys(userToToken(userId));
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 获取当前身份令牌
     *
     * @return 身份令牌 token
     */
    public static String getToken() {
        AuthInfo authInfo = authInfos.get();
        return authInfo != null ? authInfo.getToken() : null;
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Integer getUserId() {
        AuthInfo authInfo = authInfos.get();
        return authInfo != null ? authInfo.getUserId() : null;
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    public static Object getInfo() {
        AuthInfo authInfo = authInfos.get();
        return authInfo != null ? authInfo.getInfo() : null;
    }

    /**
     * 根据匹配模式，查询存储 token 的精准 RedisKey
     *
     * @param pattern 身份令牌
     * @return Redis Key
     */
    private static String getRedisKey(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && keys.size() > 0) {
            return keys.stream().findFirst().get();
        }
        return null;
    }

    /**
     * 用户 token
     *
     * @param token  用户登录令牌
     * @param userId 用户ID
     */
    public static String userToken(String token, Integer userId) {
        String tokenPrefix = authConfig.getTokenPrefix();
        String redisKey = token + REDIS_SEPARATOR + userId;
        if (StringUtils.isNotBlank(tokenPrefix)) {
            redisKey = tokenPrefix + REDIS_SEPARATOR + redisKey;
        }
        return redisKey;
    }

    /**
     * token 查询用户的模糊匹配 key
     *
     * @param token 用户 token
     */
    public static String tokenToUser(String token) {
        String tokenPrefix = authConfig.getTokenPrefix();
        String redisKey = token + REDIS_SEPARATOR + "*";
        if (StringUtils.isNotBlank(tokenPrefix)) {
            redisKey = tokenPrefix + REDIS_SEPARATOR + redisKey;
        }
        return redisKey;
    }

    /**
     * 用户ID 查询 token的模糊匹配 key
     *
     * @param userId 用户ID
     */
    public static String userToToken(Integer userId) {
        String tokenPrefix = authConfig.getTokenPrefix();
        String redisKey = "*" + REDIS_SEPARATOR + userId;
        if (StringUtils.isNotBlank(tokenPrefix)) {
            redisKey = tokenPrefix + REDIS_SEPARATOR + redisKey;
        }
        return redisKey;
    }
}

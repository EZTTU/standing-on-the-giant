package org.cuit.echo.seon.commonauth;

import lombok.Data;

/**
 * @author SeonExlike
 * @date 2022/10/12
 */
@Data
public class AuthInfo {

    private String token;

    private String redisKey;

    private Integer userId;

    private Object info;
}

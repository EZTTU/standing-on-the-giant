package org.cuit.echo.seon.commonauth.annotation;

import java.lang.annotation.*;

/**
 * 忽略认证拦截
 *
 * @author SeonExlike
 * @date 2023/2/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresAuth {

    boolean value() default true;
}

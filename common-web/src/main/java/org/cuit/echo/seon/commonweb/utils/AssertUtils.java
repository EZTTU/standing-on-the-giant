package org.cuit.echo.seon.commonweb.utils;


import org.apache.commons.lang3.StringUtils;
import org.cuit.echo.seon.utils.exception.BusinessException;

/**
 * @author SeonExlike
 * @date 2018-11-14
 */
public class AssertUtils {

    public static void isBlank(String str, String message) {
        AssertUtils.isTrue(StringUtils.isBlank(str), message);
    }

    public static void isNotBlank(String str, String message) {
        AssertUtils.isTrue(StringUtils.isNotBlank(str), message);
    }

    public static void isNull(Object obj, String message) {
        AssertUtils.isTrue(obj == null, message);
    }

    public static void isNotNull(Object obj, String message) {
        AssertUtils.isTrue(obj != null, message);
    }

    /**
     * 如果 bool 为 false，则抛出异常
     *
     * @param bool    条件
     * @param message 抛出异常时附带的消息
     */
    public static void isTrue(boolean bool, String message) {
        isTrue(bool, message, 500);
    }

    /**
     * 如果 bool 为 false，则抛出异常
     *
     * @param bool    条件
     * @param message 抛出异常时附带的消息
     * @param code    错误码
     */
    public static void isTrue(boolean bool, String message, int code) {
        if (!bool) {
            throw new BusinessException(message, code);
        }
    }

    /**
     * 如果 bool 为 true，则抛出异常
     *
     * @param bool    条件
     * @param message 抛出异常时附带的消息
     */
    public static void isFalse(boolean bool, String message) {
        isTrue(!bool, message, 500);
    }

    /**
     * 如果 bool 为 true，则抛出异常
     *
     * @param bool    条件
     * @param message 抛出异常时附带的消息
     * @param code    错误码
     */
    public static void isFalse(boolean bool, String message, int code) {
        isTrue(!bool, message, code);
    }
}

package org.cuit.echo.seon.utils.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义的异常类
 *
 * @author Seon
 * @date 2022/10/25 17:02
 */
@Setter
@Getter
public class BusinessException extends RuntimeException {
    private String msg;
    private int code = 500;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(String msg, Throwable e) {
        super(msg, e);
        this.msg = e.getMessage();
    }

    public BusinessException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BusinessException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}

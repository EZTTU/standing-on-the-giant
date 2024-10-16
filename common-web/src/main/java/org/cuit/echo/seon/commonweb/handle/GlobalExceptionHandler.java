package org.cuit.echo.seon.commonweb.handle;


import lombok.extern.slf4j.Slf4j;
import org.cuit.echo.seon.commonweb.response.R;
import org.cuit.echo.seon.utils.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;


/**
 * @author Seon
 * @date 2022/11/13 13:22
 */
@Slf4j
@RestControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler {
    private static final String DUPLICATE_KEY = "Duplicate entry";

    @ExceptionHandler(BusinessException.class)
    public R<Void> businessException(BusinessException e) {
        return R.error(e.getMessage(), e.getCode());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<Void> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        if (e.getMessage().contains(DUPLICATE_KEY)) {
            String[] split = e.getMessage().split(" ");
            String msg = "失败，" + split[2] + "已经存在了，改一个吧";
            return R.error(msg, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return R.error("失败，请检查信息", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.debug("HttpMessageNotReadableException", e);
        return R.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.debug("MethodArgumentNotValidException", e);
        return R.error(msg, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> constraintViolationException(ConstraintViolationException e) {
        Iterator<ConstraintViolation<?>> iterator = e.getConstraintViolations().iterator();
        ConstraintViolation<?> v = iterator.next();
        log.debug("ConstraintViolationException", e);
        return R.error(v.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(BindException.class)
    public R<Void> bindException(BindException e) {
        log.debug("BindException", e);
        return R.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public R<Void> authenticationException(AuthenticationException e) {
//        log.debug("AuthenticationException", e);
//        return R.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<Void> exception(Exception e) {
        log.error(e.getMessage(), e);
        return R.error("未知错误", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

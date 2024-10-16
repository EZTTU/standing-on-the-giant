package org.cuit.echo.seon.commonweb.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author SeonExlike
 * @date 2022年11月12日01:03:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 结果数据
     */
    private T result;

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.result = object;
        r.code = HttpStatus.OK.value();
        r.msg = "success";
        return r;
    }

    public static <T> R<T> error(String message, Integer errorCode) {
        R<T> r = new R<>();
        r.code = errorCode;
        r.msg = message;
        return r;
    }
}
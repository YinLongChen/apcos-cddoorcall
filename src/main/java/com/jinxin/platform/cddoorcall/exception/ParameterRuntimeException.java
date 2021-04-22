package com.jinxin.platform.cddoorcall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义运行时异常基础类
 *
 * @author yangjie
 */
@Getter
public class ParameterRuntimeException extends RuntimeException {
    private int code = 400;

    private String message;

    public ParameterRuntimeException() {
        super();
    }

    public ParameterRuntimeException(Exception exception) {
        super(exception);
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    public ParameterRuntimeException(String message) {
        super(message);
        this.code = HttpStatus.BAD_REQUEST.value();
        this.message = message;
    }

    public ParameterRuntimeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }


}

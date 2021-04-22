package com.jinxin.platform.cddoorcall.exception;

import com.jinxin.platform.base.common.pojo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;


/**
 * 统一异常处理
 *
 * @author yangjie
 */
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class MyExceptionHandle {
    private String modulePre = "门禁呼叫模块,>>>";

    @ExceptionHandler(value = Exception.class)
    public JsonResult exception(Exception exception, HttpServletRequest request) {
        if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            log.info(modulePre + "， 参数检验失败--> {}", bindException.getMessage());
            List<ObjectError> allErrors = bindException.getBindingResult().getAllErrors();
            return JsonResult.error(HttpStatus.BAD_REQUEST.value(), allErrors.iterator().next().getDefaultMessage());
        }
        log.error(modulePre + "，请求:{}, Exception:{}", request.getRequestURL(), exception);
        return JsonResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), getExceptionInfo(exception));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult runtimeException(Exception exception, HttpServletRequest request) {
        log.error(modulePre + "， 请求:{}, runtimeException异常:{}", request.getRequestURL(), exception);
        String message = exception.getMessage();
        return JsonResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    @ExceptionHandler(value = ParameterRuntimeException.class)
    public JsonResult businessRuntimeException(ParameterRuntimeException exception, HttpServletRequest request) {
        log.error(modulePre + "，请求:{}, ParameterRuntimeException调用异常:{}", request.getRequestURL(), exception.getMessage());
        return JsonResult.error(exception.getCode(), getExceptionInfo(exception));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult exception(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error(modulePre + "，请求:{}, MethodArgumentNotValidException调用异常:{}", request.getRequestURL(), exception.getMessage());
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        StringBuilder stringBuilder = new StringBuilder();

        allErrors.forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;
            stringBuilder.append(fieldError.getObjectName()).append("的参数--");
            stringBuilder.append(fieldError.getField()).append(",");
            stringBuilder.append(fieldError.getDefaultMessage()).append(";");
        });
        return JsonResult.error(400, stringBuilder.toString());
    }

    @ExceptionHandler(value = ValidationException.class)
    public JsonResult handException(ValidationException e) {
        log.info(modulePre + "， 参数检验失败--> {}", e.getMessage());
        return JsonResult.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    private String getExceptionInfo(Exception exception) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        exception.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.warn("异常信息为{}", ret);
        return exception.getMessage();
//        return ret;
    }
}

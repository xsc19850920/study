package com.sxia.validate.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Title：统一异常，返回json
 * Description：
 * @author WZQ
 * @version 1.0.0
 * @date 2021/4/22
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object>  exceptionHandler(Exception e) {
        log.error("出现了异常!:{}", e.getMessage());
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    /**
     * 空参异常处理
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> bindException(BindException ex, HttpServletRequest request) {
        log.warn("BindException:", ex);
        try {
            // 拿到@NotNull,@NotBlank和 @NotEmpty等注解上的message值
            String msg = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
            if (StrUtil.isNotEmpty(msg)) {
                // 自定义状态返回
                return new ResponseEntity<Object>(msg, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ignored) {
        }
        // 参数类型不匹配检验
        StringBuilder msg = new StringBuilder();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        fieldErrors.forEach((oe) ->
                msg.append("参数:[").append(oe.getObjectName())
                        .append(".").append(oe.getField())
                        .append("]的传入值:[").append(oe.getRejectedValue()).append("]与预期的字段类型不匹配.")
        );
        return new ResponseEntity<Object>(msg, HttpStatus.BAD_REQUEST);
    }

    /**
     * jsr 规范中的验证异常，嵌套检验问题
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
//        log.warn("ConstraintViolationException:", ex);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        Map<String, String> map = violations.stream().collect(Collectors.toMap(item -> ((PathImpl)(item.getPropertyPath())).getLeafNode().getName(), item -> item.getMessage()));
//        ConstraintViolation<?> violation = violations.iterator().next();
//        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
//        String message2 = String.format("%s:%s", path, violation.getMessage());

        return new ResponseEntity<Object> ( map,HttpStatus.BAD_REQUEST);
    }

    /**
     * spring 封装的参数验证异常， 在controller中没有写result参数时，会进入
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        log.warn("MethodArgumentNotValidException:", ex);
        return new ResponseEntity<Object> ( ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(item -> ((FieldError) item).getField(), item -> item.getDefaultMessage())),HttpStatus.BAD_REQUEST);
    }
}

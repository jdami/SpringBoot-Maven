package com.somnus.springboot.commons.base.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.somnus.springboot.commons.base.exception.ParamValidException;
import com.somnus.springboot.commons.base.exception.ServiceException;
import com.somnus.springboot.commons.base.exception.UserNotLoginException;
import com.somnus.springboot.commons.base.result.LogicResult;

/**
 * @ClassName: GlobalRestControllerExceptionResolver
 * @Description: 异常处理类
 * @author Somnus
 * @date 2018年8月24日
 */
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalRestControllerExceptionResolver {

    /**
     * 业务异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public LogicResult allServiceExceptionHandler(ServiceException e) {
        log.warn("业务处理失败:" + e.getMessage());
        return LogicResult.builder().build().fail(e.getMessage());
    }
    
    /**
     * 所有异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public LogicResult allIllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.warn("业务参数检查未通过:" + e.getMessage());
        return LogicResult.builder().build().fail(e.getMessage());
    }

    /**
     * 参数校验异常报错
     *
     * @param e 参数校验异常
     */
    @ExceptionHandler(value = ParamValidException.class)
    public LogicResult paramValidException(ParamValidException e) {
    	log.warn("业务参数检查未通过:" + e.getMessage());
        return LogicResult.builder().build().fail(e.getMessage());
    }

    /**
     * 所有异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = UserNotLoginException.class)
    public LogicResult allUserNotLoginExceptionHandler(UserNotLoginException e) {
        log.warn("用户未登录!");
        return LogicResult.builder().build().noLogin();
    }

    /**
     * 所有异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = Exception.class)
    public LogicResult allExceptionHandler(Exception e) {
        log.error("业务处理异常:", e);
        return LogicResult.builder().build().error("系统打盹中，您的请求暂时无法处理。");
    }
}

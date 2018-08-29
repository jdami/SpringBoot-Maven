package com.somnus.springboot.commons.base.exception;

/**
 * @ClassName: ParamValidException
 * @Description: 参数校验失败异常类
 * @author pc
 * @date 2018年8月24日
 */
public class ParamValidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ParamValidException() {
        super();
    }

    public ParamValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamValidException(String message) {
        super(message);
    }

    public ParamValidException(Throwable cause) {
        super(cause);
    }
}

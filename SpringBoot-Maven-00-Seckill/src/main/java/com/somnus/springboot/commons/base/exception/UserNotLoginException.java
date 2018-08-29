package com.somnus.springboot.commons.base.exception;

/**
 * @author jiangjk
 */
public class UserNotLoginException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserNotLoginException() {
        super();
    }

    public UserNotLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotLoginException(String message) {
        super(message);
    }

    public UserNotLoginException(Throwable cause) {
        super(cause);
    }
}

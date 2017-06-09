package com.qiangxi.exception;

/**
 * 自定义异常，用于处理RatingBarView中的异常
 */
public class IllegalNumberException extends RuntimeException {

    public IllegalNumberException() {
        super();
    }

    public IllegalNumberException(String message) {
        super(message);
    }

    public IllegalNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNumberException(Throwable cause) {
        super(cause);
    }

}

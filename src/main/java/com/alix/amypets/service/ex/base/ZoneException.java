package com.alix.amypets.service.ex.base;

/**
 * 用户空间异常
 */
public class ZoneException extends ServiceException{
    public ZoneException() {
        super();
    }

    public ZoneException(String message) {
        super(message);
    }

    public ZoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZoneException(Throwable cause) {
        super(cause);
    }

    protected ZoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

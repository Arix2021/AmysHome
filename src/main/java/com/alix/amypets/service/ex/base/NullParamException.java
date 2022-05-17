package com.alix.amypets.service.ex.base;

import com.alix.amypets.service.ex.base.ServiceException;

/**
 * 空参数异常
 */
public class NullParamException extends ServiceException {
    public NullParamException() {
        super();
    }

    public NullParamException(String message) {
        super(message);
    }

    public NullParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullParamException(Throwable cause) {
        super(cause);
    }

    protected NullParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

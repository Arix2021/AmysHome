package com.alix.amypets.service.ex.user;

import com.alix.amypets.service.ex.base.ServiceException;

/**
 * 用户身份异常
 */
public class UserIdentityException extends ServiceException {
    public UserIdentityException() {
        super();
    }

    public UserIdentityException(String message) {
        super(message);
    }

    public UserIdentityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdentityException(Throwable cause) {
        super(cause);
    }

    protected UserIdentityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

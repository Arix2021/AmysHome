package com.alix.amypets.service.ex.user;

import com.alix.amypets.service.ex.base.ServiceException;
import com.alix.amypets.service.ex.base.UserException;

/**
 * 密码不正确
 */
public class PasswordException extends UserException {
    public PasswordException() {
        super();
    }

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordException(Throwable cause) {
        super(cause);
    }

    protected PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

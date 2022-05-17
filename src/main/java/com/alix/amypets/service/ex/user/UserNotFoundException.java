package com.alix.amypets.service.ex.user;

import com.alix.amypets.service.ex.base.ServiceException;
import com.alix.amypets.service.ex.base.UserException;

/**
 * 用户未找到
 */
public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

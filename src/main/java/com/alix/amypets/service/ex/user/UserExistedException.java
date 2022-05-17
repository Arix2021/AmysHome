package com.alix.amypets.service.ex.user;

import com.alix.amypets.service.ex.base.ServiceException;
import com.alix.amypets.service.ex.base.UserException;

/**
 * 用户已存在
 */
public class UserExistedException extends UserException {
    public UserExistedException() {
        super();
    }

    public UserExistedException(String message) {
        super(message);
    }

    public UserExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistedException(Throwable cause) {
        super(cause);
    }

    protected UserExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

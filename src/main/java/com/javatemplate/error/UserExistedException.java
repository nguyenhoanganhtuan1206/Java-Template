package com.javatemplate.error;

import org.springframework.http.HttpStatus;

public class UserExistedException extends DomainException {
    public UserExistedException(String message, Object... args) {
        super(HttpStatus.CONFLICT, message, args);
    }
}

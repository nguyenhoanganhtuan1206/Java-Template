package com.javatemplate.error;

import org.springframework.http.HttpStatus;

import static java.lang.String.format;

public class UsernameNotFoundException extends DomainException {
    public UsernameNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, format("User not found with username %s", username));
    }
}

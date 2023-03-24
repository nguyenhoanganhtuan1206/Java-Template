package com.javatemplate.error;

import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends DomainException {
    public UsernameNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "User not found with username %s", username);
    }
}

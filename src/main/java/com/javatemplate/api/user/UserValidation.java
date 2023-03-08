package com.javatemplate.api.user;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

import static com.javatemplate.error.CommonError.supplyValidationError;
import static org.apache.commons.lang3.StringUtils.isBlank;

@UtilityClass
public class UserValidation {

    public static void validateUserCreate(final User user) {
        validateUserName(user.getUsername());
        validatePassword(user.getPassword());
    }

    public static void validateUserUpdate(final User user) {
        validateUserName(user.getUsername());
    }

    private static void validateUserName(String username) {
        if (username == null || isBlank(username)) {
            throw supplyValidationError("Username is required. Please check your inputs again").get();
        }
    }

    private static void validatePassword(String password) {
        if (password == null || isBlank(password)) {
            throw supplyValidationError("Password is required. Please check your inputs again").get();
        }
    }
}

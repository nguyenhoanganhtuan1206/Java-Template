package com.javatemplate.api.user;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

import static com.javatemplate.error.CommonError.supplyValidationError;
import static org.apache.commons.lang3.StringUtils.*;

@UtilityClass
public class UserValidation {

    public static void validateUserCreate(final User user) {
        validateUserName(user.getUsername());

        validatePasswordNotEmpty(user.getPassword());
        validateLengthPassword(user.getPassword());
    }

    public static void validateUserUpdate(final User user) {
        validateUserName(user.getUsername());

        validateLengthPassword(user.getPassword());
    }

    private static void validateUserName(final String username) {
        if (isBlank(username)) {
            throw supplyValidationError("Username cannot be blank").get();
        }
    }

    private static void validateLengthPassword(final String password) {
        if (isNotBlank(password) && length(password) < 6) {
            throw supplyValidationError("Password must be at least 6 characters").get();
        }
    }

    private static void validatePasswordNotEmpty(final String password) {
        if (isBlank(password)) {
            throw supplyValidationError("Password cannot be blank").get();
        }
    }
}

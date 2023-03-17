package com.javatemplate.error;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class CommonError {

    public static Supplier<BadRequestException> supplyValidationError(final String message) {
        return () -> new BadRequestException(message);
    }

    public static Supplier<AccessDeniedException> supplyAccessDeniedError(final String message) {
        return () -> new AccessDeniedException(message);
    }
}

package com.javatemplate.domain.user;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class UserError {

    public static <T> Supplier<NotFoundException> supplyUserNotFound(final T input) {
        return () -> new NotFoundException("Username %s could not be found", input);
    }

    public static Supplier<BadRequestException> supplyUserExisted(final String username) {
        return () -> new BadRequestException("Username %s has been taken", username);
    }
}

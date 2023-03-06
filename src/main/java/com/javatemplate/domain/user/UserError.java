package com.javatemplate.domain.user;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class UserError {

    public static Supplier<NotFoundException> supplyUserNotFound(final UUID id) {
        return () -> new NotFoundException("User with id %s not found", id);
    }

    public static Supplier<BadRequestException> supplyUserExisted(final String username) {
        return () -> new BadRequestException("User %s is already existing", username);
    }
}

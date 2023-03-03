package com.javatemplate.domain.user;

import com.javatemplate.error.NotFoundException;
import com.javatemplate.error.UserExistedException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class UserError {

    public static Supplier<NotFoundException> supplyUserNotFound(final UUID id) {
        return () -> new NotFoundException("User with id %s not found", id);
    }

    public static Supplier<UserExistedException> supplyUserExisted(final String username) {
        return () -> new UserExistedException("User %s is already existing", username);
    }
}

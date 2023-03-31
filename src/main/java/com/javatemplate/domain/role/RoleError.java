package com.javatemplate.domain.role;

import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class RoleError {

    public static <T> Supplier<NotFoundException> supplyRoleNotFound(final T input) {
        return () -> new NotFoundException("Role %s could not be found", input);
    }
}

package com.javatemplate.api.user;

import com.javatemplate.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.javatemplate.api.user.UserValidation.validateUserCreate;
import static com.javatemplate.api.user.UserValidation.validateUserUpdate;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidationTest {

    @Test
    void shouldValidateUserCreate_OK() {
        final var user = buildUser();

        validateUserCreate(user);
    }

    @Test
    void shouldValidateUserCreate_ThrownValidationError() {
        final var user = buildUser();
        user.setUsername(null);

        assertThrows(BadRequestException.class, () -> validateUserCreate(user));
    }

    @Test
    void shouldValidateUserUpdate_Ok() {
        final var user = buildUser();

        validateUserUpdate(user);
    }

    @Test
    void shouldValidateUserUpdate_ThrownValidationError() {
        final var user = buildUser();
        user.setUsername(null);

        assertThrows(BadRequestException.class, () -> validateUserUpdate(user));
    }
}

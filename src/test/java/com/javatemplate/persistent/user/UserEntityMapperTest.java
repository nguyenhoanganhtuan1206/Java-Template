package com.javatemplate.persistent.user;

import org.junit.jupiter.api.Test;

import static com.javatemplate.fakes.UserFakes.*;
import static com.javatemplate.persistent.user.UserEntityMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityMapperTest {

    @Test
    void shouldToUser_Ok() {
        final var userEntity = buildUserEntity();
        final var user = toUser(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getFirstName(), user.getFirstName());
        assertEquals(userEntity.getLastName(), user.getLastName());
        assertEquals(userEntity.getAvatar(), user.getAvatar());
        assertEquals(userEntity.getEnabled(), user.getEnabled());
    }

    @Test
    void shouldToUsers_Ok() {
        final var userEntity = builderUserEntities();
        final var users = toUsers(userEntity);

        assertEquals(userEntity.size(), users.size());
    }

    @Test
    void shouldToUserEntity_Ok() {
        final var user = buildUser();
        final var userEntity = toUserEntity(user);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getFirstName(), user.getFirstName());
        assertEquals(userEntity.getLastName(), user.getLastName());
        assertEquals(userEntity.getAvatar(), user.getAvatar());
        assertEquals(userEntity.getEnabled(), user.getEnabled());
    }
}

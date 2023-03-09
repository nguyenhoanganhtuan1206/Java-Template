package com.javatemplate.fakes;

import com.javatemplate.domain.user.User;
import com.javatemplate.persistent.user.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class UserFakes {

    public static User buildUser() {
        return User.builder()
                .id(randomUUID())
                .username(randomAlphabetic(3, 10))
                .password(randomAlphabetic(6, 10))
                .firstName(randomAlphabetic(3, 10))
                .lastName(randomAlphabetic(3, 10))
                .avatar(randomAlphabetic(3, 10))
                .enabled(Boolean.TRUE)
                .roleId(randomUUID())
                .build();
    }

    public static List<User> buildUsers() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildUser())
                .toList();
    }

    public static UserEntity buildUserEntity() {
        return UserEntity.builder()
                .id(randomUUID())
                .username(randomAlphabetic(3, 10))
                .firstName(randomAlphabetic(3, 10))
                .lastName(randomAlphabetic(3, 10))
                .avatar(randomAlphabetic(3, 10))
                .enabled(Boolean.TRUE)
                .roleId(randomUUID())
                .build();
    }

    public static List<UserEntity> builderUserEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildUserEntity())
                .toList();
    }
}

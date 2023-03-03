package com.javatemplate.persistent.user;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserEntityMapper {

    public static User toUser(final UserEntity user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .enabled(user.getEnabled())
                .build();
    }

    public static List<User> toUsers(final List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserEntityMapper::toUser)
                .toList();
    }
}

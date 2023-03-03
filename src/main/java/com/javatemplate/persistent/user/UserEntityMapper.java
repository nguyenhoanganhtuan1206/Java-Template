package com.javatemplate.persistent.user;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;


@UtilityClass
public class UserEntityMapper {

    public static User toUser(final UserEntity user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .enabled(user.getEnabled())
                .roleId(user.getRoleId())
                .build();
    }

    public static List<User> toUsers(final List<UserEntity> userEntities) {
        return emptyIfNull(userEntities.stream()
                .map(UserEntityMapper::toUser)
                .toList());
    }

    public static UserEntity toUserEntity(final User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .enabled(user.getEnabled())
                .build();
    }
}

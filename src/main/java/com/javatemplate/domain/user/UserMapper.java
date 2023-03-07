package com.javatemplate.domain.user;

import com.javatemplate.api.user.UserDTORequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(final UserDTORequest userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .avatar(userDTO.getAvatar())
                .enabled(userDTO.getEnabled())
                .roleId(userDTO.getRoleId())
                .build();
    }
}

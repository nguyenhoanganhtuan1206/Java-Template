package com.javatemplate.api.user;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserDTOMapper {

    public static UserDTO toUserDTO(final User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .enabled(user.getEnabled())
                .role(user.getRole())
                .build();
    }

    public static List<UserDTO> toUsersDTO(final List<User> users) {
        return users.stream()
                .map(UserDTOMapper::toUserDTO)
                .toList();
    }
}

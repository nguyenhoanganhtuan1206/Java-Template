package com.javatemplate.api.user;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class UserDTOMapper {

    public static UserResponseDTO toUserDTO(final User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .enabled(user.getEnabled())
                .build();
    }

    public static List<UserResponseDTO> toUsersDTO(final List<User> users) {
        return emptyIfNull(users.stream()
                .map(UserDTOMapper::toUserDTO)
                .toList());
    }
}

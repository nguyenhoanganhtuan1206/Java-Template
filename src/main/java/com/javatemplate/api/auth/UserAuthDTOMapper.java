package com.javatemplate.api.auth;

import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAuthDTOMapper {

    public static User toUser(final UserAuthRequestDTO userAuthRequestDTO) {
        return User.builder()
                .username(userAuthRequestDTO.getUsername())
                .password(userAuthRequestDTO.getPassword())
                .build();
    }

    public static UserAuthResponseDTO toUserAuthDTO(final User user) {
        return UserAuthResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .enabled(user.getEnabled())
                .build();
    }
}

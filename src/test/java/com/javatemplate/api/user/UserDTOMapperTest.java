package com.javatemplate.api.user;

import org.junit.jupiter.api.Test;

import static com.javatemplate.api.user.UserDTOMapper.toUserDTO;
import static com.javatemplate.api.user.UserDTOMapper.toUsersDTO;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static com.javatemplate.fakes.UserFakes.buildUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOMapperTest {

    @Test
    void shouldToUserDTO_Ok() {
        final var user = buildUser();
        final var userDTO = toUserDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getAvatar(), userDTO.getAvatar());
        assertEquals(user.getEnabled(), userDTO.getEnabled());
    }

    @Test
    void shouldToUsersDTO_Ok() {
        final var users = buildUsers();
        final var userDTOS = toUsersDTO(users);

        assertEquals(userDTOS.size(), users.size());
    }
}

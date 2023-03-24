package com.javatemplate.api.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginDTOMapperTest {

    @Test
    void toAuthentication() {
        LoginDTO loginDTO = LoginDTO.builder()
                .username("user")
                .password("123456")
                .build();

        Authentication authentication = LoginDTOMapper.toAuthentication(loginDTO);

        assertEquals(loginDTO.getUsername(), authentication.getPrincipal());
        assertEquals(loginDTO.getPassword(), authentication.getCredentials());
    }
}

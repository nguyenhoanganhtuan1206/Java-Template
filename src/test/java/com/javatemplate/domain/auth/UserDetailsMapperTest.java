package com.javatemplate.domain.auth;

import org.junit.jupiter.api.Test;

import static com.javatemplate.domain.auth.UserDetailsMapper.toUserDetails;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsMapperTest {

    @Test
    void shouldToUserDetails_OK() {
        final var user = buildUser();
        final var userDetail = toUserDetails(user, "CONTRIBUTOR");

        assertEquals(user.getUsername(), userDetail.getUsername());
        assertEquals(user.getPassword(), userDetail.getPassword());
    }
}

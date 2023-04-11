package com.javatemplate.api.user;

import org.junit.jupiter.api.Test;

import static com.javatemplate.api.user.SocialUserMapper.toSocialUser;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SocialUserMapperTest {

    @Test
    void shouldToSocialUser_OK() {
        final var user = buildUser();
        final var socialUser = toSocialUser(user);

        assertEquals(user.getUsername(), socialUser.getId());
        assertEquals(user.getFirstName(), socialUser.getFirstName());
        assertEquals(user.getLastName(), socialUser.getLastName());
    }
}

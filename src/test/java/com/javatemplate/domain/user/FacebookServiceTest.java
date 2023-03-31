package com.javatemplate.domain.user;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FacebookServiceTest {

    @Test
    public void testSocialUserCreation() {
        final SocialUser socialUserWithEmail = SocialUser.builder()
                .firstName(randomAlphabetic(6, 10))
                .lastName(randomAlphabetic(6, 10))
                .username(randomAlphabetic(6, 10)).build();
        assertNotNull(socialUserWithEmail.getFirstName());
        assertNotNull(socialUserWithEmail.getLastName());
        assertNotNull(socialUserWithEmail.getUsername());
    }
}

package com.javatemplate.fakes;

import com.javatemplate.domain.user.SocialUser;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class SocialUserFakes {

    public static SocialUser buildSocialUser() {
        return SocialUser.builder()
                .username(randomAlphabetic(6, 10))
                .firstName(randomAlphabetic(6, 10))
                .lastName(randomAlphabetic(6, 10))
                .build();
    }
}
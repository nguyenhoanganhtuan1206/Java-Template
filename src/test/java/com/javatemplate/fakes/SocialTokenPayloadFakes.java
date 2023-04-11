package com.javatemplate.fakes;

import com.javatemplate.domain.auth.SocialTokenPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocialTokenPayloadFakes {

    public static SocialTokenPayload buildTokenSocial() {
        return SocialTokenPayload.builder()
                .name("test")
                .username("test")
                .firstName("test")
                .lastName("test")
                .build();
    }
}

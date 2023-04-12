package com.javatemplate.fakes;

import com.javatemplate.domain.auth.SocialTokenPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocialTokenPayloadFakes {

    public static SocialTokenPayload buildTokenSocial() {
        return SocialTokenPayload.builder()
                .name("tuan123")
                .username("anh tuan")
                .firstName("anh")
                .lastName("tuan")
                .build();
    }
}

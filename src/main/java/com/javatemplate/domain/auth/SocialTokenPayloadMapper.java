package com.javatemplate.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.experimental.UtilityClass;
import org.springframework.social.facebook.api.User;

@UtilityClass
public class SocialTokenPayloadMapper {

    public static SocialTokenPayload toSocialTokenPayloadFromGoogle(final GoogleIdToken.Payload payload) {
        return SocialTokenPayload.builder()
                .email(payload.getEmail())
                .username(String.valueOf(payload.get("name")))
                .firstName(String.valueOf(payload.get("family_name")))
                .lastName(String.valueOf(payload.get("given_name")))
                .build();
    }

    public static SocialTokenPayload toSocialTokenPayloadFromFacebook(final User user) {
        return SocialTokenPayload.builder()
                .email(user.getEmail())
                .username(user.getId())
                .name(user.getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}

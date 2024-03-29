package com.javatemplate.domain.auth;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import static com.javatemplate.domain.auth.SocialTokenPayloadMapper.toSocialTokenPayloadFromFacebook;

@Service
public class FacebookTokenVerifierService {

    public SocialTokenPayload verifyFacebookIdToken(final String tokenAccess) {
        final Facebook facebook = new FacebookTemplate(tokenAccess);
        final User user = facebook.fetchObject("me", User.class, "id", "email", "first_name", "last_name");
        return toSocialTokenPayloadFromFacebook(user);
    }
}

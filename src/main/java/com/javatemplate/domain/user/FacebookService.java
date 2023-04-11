package com.javatemplate.domain.user;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
public class FacebookService {

    public SocialUser parseToken(final String facebookToken) {
        final Facebook facebook = new FacebookTemplate(facebookToken);
        final User userLogin = facebook.fetchObject("me", User.class, "id", "email", "first_name", "last_name");

        return SocialUser.builder()
                .firstName(userLogin.getFirstName())
                .lastName(userLogin.getLastName())
                .id(isEmpty(userLogin.getEmail()) ? userLogin.getEmail() : userLogin.getId())
                .build();
    }
}

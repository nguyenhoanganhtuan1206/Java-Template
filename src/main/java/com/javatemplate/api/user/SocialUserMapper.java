package com.javatemplate.api.user;

import com.javatemplate.domain.user.SocialUser;
import com.javatemplate.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocialUserMapper {

    public static SocialUser toSocialUser(final User user) {
        return SocialUser.builder()
                .id(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}

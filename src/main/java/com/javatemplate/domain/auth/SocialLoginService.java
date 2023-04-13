package com.javatemplate.domain.auth;

import com.javatemplate.domain.user.User;
import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.javatemplate.domain.auth.UserDetailsMapper.toUserDetails;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final GoogleTokenVerifierService googleTokenVerifierService;

    private final FacebookTokenVerifierService facebookTokenVerifierService;

    private final UserStore userStore;

    private final RoleStore roleStore;

    public UserDetails loginWithGoogle(final String tokenAccess) {
        final SocialTokenPayload googleAccount = googleTokenVerifierService.verifyGoogleIdToken(tokenAccess);

        return getJwtUserDetails(googleAccount);
    }

    public UserDetails loginWithFacebook(final String tokenAccess) {
        final SocialTokenPayload facebookAccount = facebookTokenVerifierService.verifyFacebookIdToken(tokenAccess);

        return getJwtUserDetails(facebookAccount);
    }

    private UserDetails getJwtUserDetails(final SocialTokenPayload socialTokenPayload) {
        return userStore.findByUsername(socialTokenPayload.getUsername())
                .map(user -> toUserDetails(user, "CONTRIBUTOR"))
                .orElse(toUserDetails(createNewUserFromSocialToken(socialTokenPayload), "CONTRIBUTOR"));
    }

    private User createNewUserFromSocialToken(final SocialTokenPayload socialTokenPayload) {
        final UUID roleId = roleStore.findByName("CONTRIBUTOR").getId();
        final User user = User.builder()
                .username(socialTokenPayload.getUsername())
                .password(randomUUID().toString())
                .firstName(socialTokenPayload.getFirstName())
                .lastName(socialTokenPayload.getLastName())
                .enabled(true)
                .roleId(roleId)
                .build();

        return userStore.create(user);
    }
}

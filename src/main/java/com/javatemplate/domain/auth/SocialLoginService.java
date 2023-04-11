package com.javatemplate.domain.auth;

import com.javatemplate.domain.user.User;
import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

        return getJwtUserDetails(googleAccount, "google");
    }

    public UserDetails loginWithFacebook(final String tokenAccess) {
        final SocialTokenPayload facebookAccount = facebookTokenVerifierService.verifyFacebookIdToken(tokenAccess);

        return getJwtUserDetails(facebookAccount, "facebook");
    }

    private UserDetails getJwtUserDetails(final SocialTokenPayload socialTokenPayload, final String loginType) {
        return userStore.findByUsername(loginType.equals("facebook") ? socialTokenPayload.getUsername() : socialTokenPayload.getEmail())
                .map(user -> toUserDetails(user, "CONTRIBUTOR"))
                .orElseGet(() -> {
                    final User user = createNewUserFromSocialToken(socialTokenPayload, "facebook");

                    return toUserDetails(user, "CONTRIBUTOR");
                });
    }

    private User createNewUserFromSocialToken(final SocialTokenPayload socialTokenPayload, final String loginType) {
        final User user = User.builder()
                .password(randomUUID().toString())
                .firstName(socialTokenPayload.getFirstName())
                .lastName(socialTokenPayload.getLastName())
                .enabled(true)
                .roleId(roleStore.findByName("CONTRIBUTOR").getId())
                .build();

        if (loginType.equals("facebook")) {
            user.setUsername(socialTokenPayload.getUsername());
        } else {
            user.setUsername(socialTokenPayload.getEmail());
        }

        return userStore.create(user);
    }
}

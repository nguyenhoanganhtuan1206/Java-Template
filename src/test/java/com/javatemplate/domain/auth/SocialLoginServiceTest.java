package com.javatemplate.domain.auth;

import com.javatemplate.domain.role.Role;
import com.javatemplate.domain.user.User;
import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static com.javatemplate.domain.auth.UserDetailsMapper.toUserDetails;
import static com.javatemplate.fakes.RoleFakes.buildRole;
import static com.javatemplate.fakes.SocialTokenPayloadFakes.buildTokenSocial;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialLoginServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private GoogleTokenVerifierService googleTokenVerifierService;

    @Mock
    private FacebookTokenVerifierService facebookTokenVerifierService;

    @Mock
    private RoleStore roleStore;

    @InjectMocks
    private SocialLoginService socialLoginService;

    @Test
    void shouldLoginGoogle_OK() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final var accessToken = randomAlphabetic(3, 10);
        final var user = buildUser();
        user.setUsername(tokenPayload.getUsername());

        final var userDetails = toUserDetails(user, "CONTRIBUTOR");

        when(googleTokenVerifierService.verifyGoogleIdToken(accessToken))
                .thenReturn(tokenPayload);
        when(userStore.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        final var actual = socialLoginService.loginWithGoogle(accessToken);

        assertEquals(userDetails, actual);

        verify(googleTokenVerifierService).verifyGoogleIdToken(accessToken);
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldLoginGoogle_CreateNewUser() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final Role role = buildRole();
        final var accessToken = randomAlphabetic(3, 10);
        final User newUser = buildUser()
                .withUsername(tokenPayload.getUsername())
                .withPassword(randomUUID().toString())
                .withFirstName(tokenPayload.getFirstName())
                .withLastName(tokenPayload.getLastName())
                .withEnabled(true)
                .withRoleId(role.getId());

        when(googleTokenVerifierService.verifyGoogleIdToken(accessToken))
                .thenReturn(tokenPayload);
        when(userStore.findByUsername(tokenPayload.getUsername()))
                .thenReturn(Optional.empty());
        when(roleStore.findByName(any(String.class)))
                .thenReturn(role);

        when(userStore.create(any())).thenReturn(newUser);

        final JwtUserDetails userDetails = new JwtUserDetails(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getPassword(),
                List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));


        final var actual = socialLoginService.loginWithGoogle(accessToken);
        assertEquals(userDetails, actual);

        verify(googleTokenVerifierService).verifyGoogleIdToken(accessToken);
        verify(userStore).findByUsername(tokenPayload.getUsername());
        verify(roleStore).findByName(any(String.class));
        verify(userStore).create(any());
    }

    @Test
    void shouldLoginFacebook_OK() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final var user = buildUser().withUsername(tokenPayload.getUsername());
        final var role = buildRole();
        final var accessToken = randomAlphabetic(3, 10);

        final UserDetails userDetails = toUserDetails(user, role.getName());

        when(facebookTokenVerifierService.verifyFacebookIdToken(accessToken))
                .thenReturn(tokenPayload);
        when(userStore.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        final var actual = socialLoginService.loginWithFacebook(accessToken);

        assertEquals(userDetails, actual);

        verify(facebookTokenVerifierService).verifyFacebookIdToken(accessToken);
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldLoginFacebook_CreateNewUser() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final var role = buildRole();
        final var accessToken = randomAlphabetic(3, 10);
        final User newUser = buildUser()
                .withUsername(tokenPayload.getUsername())
                .withPassword(randomUUID().toString())
                .withFirstName(tokenPayload.getFirstName())
                .withLastName(tokenPayload.getLastName())
                .withEnabled(true)
                .withRoleId(role.getId());

        when(facebookTokenVerifierService.verifyFacebookIdToken(accessToken))
                .thenReturn(tokenPayload);
        when(userStore.findByUsername(tokenPayload.getUsername()))
                .thenReturn(Optional.empty());

        when(roleStore.findByName(any(String.class))).thenReturn(role);

        when(userStore.create(any())).thenReturn(newUser);

        final UserDetails userDetails = toUserDetails(newUser, role.getName());

        final var actual = socialLoginService.loginWithFacebook(accessToken);
        assertEquals(userDetails, actual);

        verify(facebookTokenVerifierService).verifyFacebookIdToken(accessToken);
        verify(userStore).findByUsername(tokenPayload.getUsername());
        verify(roleStore).findByName(any(String.class));
        verify(userStore).create(any());
    }
}

package com.javatemplate.domain.auth;

import com.javatemplate.error.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.javatemplate.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith({MockitoExtension.class})
class AuthsProviderTest {

    @InjectMocks
    private AuthsProvider authsProvider;

    @Test
    void getCurrentAuthentication_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(user, actual);
    }

    @Test
    void getCurrentAuthentication_ThrownUnauthorizedException() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentAuthentication());
    }

    @Test
    void getCurrentUserRole_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentAuthentication().getRole();

        assertEquals(user.getRole(), actual);
    }

    @Test
    void getCurrentUsername_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentAuthentication().getUsername();

        assertEquals(user.getUsername(), actual);
    }

    @Test
    void getCurrentUserId_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentAuthentication().getUserId();

        assertEquals(user.getUserId(), actual);
    }
}

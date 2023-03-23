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
        final var admin = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(admin);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(admin, actual);
    }

    @Test
    void getCurrentAuthentication_ThrownUnauthorizedException() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentAuthentication());
    }

    @Test
    void getCurrentUserRole_OK() {
        final var admin = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(admin);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(admin.getRole(), actual.getRole());
    }

    @Test
    void getCurrentUserId_OK() {
        final var admin = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(admin);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(admin.getUserId(), actual.getUserId());
    }
}

package com.javatemplate.api;

import com.javatemplate.domain.auth.UserAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.UUID;

public class WithMockAdminSecurityContextFactory implements WithSecurityContextFactory<WithMockAdmin> {

    @Override
    public SecurityContext createSecurityContext(WithMockAdmin annotation) {
        final var context = SecurityContextHolder.createEmptyContext();

        final Authentication auth = new UserAuthenticationToken(
                UUID.randomUUID(),
                "admin",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        context.setAuthentication(auth);
        return context;
    }
}

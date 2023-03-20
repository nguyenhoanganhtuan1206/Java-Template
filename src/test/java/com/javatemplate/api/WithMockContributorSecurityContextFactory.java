package com.javatemplate.api;

import com.javatemplate.domain.auth.UserAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.UUID;

public class WithMockContributorSecurityContextFactory implements WithSecurityContextFactory<WithMockContributor> {

    @Override
    public SecurityContext createSecurityContext(WithMockContributor annotation) {
        final var context = SecurityContextHolder.createEmptyContext();

        final Authentication auth = new UserAuthenticationToken(
                UUID.fromString("660258cd-b624-4786-a720-a1cbcc255383"),
                "user",
                List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR"))
        );
        context.setAuthentication(auth);
        return context;
    }
}
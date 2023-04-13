package com.javatemplate.fakes;

import com.javatemplate.domain.auth.UserAuthenticationToken;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class UserAuthenticationTokenFakes {

    public static UserAuthenticationToken buildAdmin() {
        return new UserAuthenticationToken(
                UUID.randomUUID(),
                "admin",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    public static UserAuthenticationToken buildContributor() {
        return new UserAuthenticationToken(
                UUID.randomUUID(),
                "contributor",
                List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));
    }
}

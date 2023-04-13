package com.javatemplate.fakes;

import com.javatemplate.domain.auth.UserAuthenticationToken;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.UUID.randomUUID;

@UtilityClass
public class UserAuthenticationTokenFakes {

    public static UserAuthenticationToken buildAdmin() {
        return new UserAuthenticationToken(
                randomUUID(),
                "admin",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    public static UserAuthenticationToken buildContributor() {
        return new UserAuthenticationToken(
                randomUUID(),
                "contributor",
                List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));
    }
}

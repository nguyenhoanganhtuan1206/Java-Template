package com.javatemplate.fakes;

import com.javatemplate.domain.auth.JwtUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static java.util.UUID.randomUUID;

@UtilityClass
public class JwtUserDetailFakes {

    public static JwtUserDetails buildJwtUserDetails() {
        return new JwtUserDetails(randomUUID(), "user", "123123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}

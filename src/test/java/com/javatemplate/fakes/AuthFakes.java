package com.javatemplate.fakes;

import com.javatemplate.domain.auth.JwtUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class AuthFakes {

    public static Authentication buildAuth() {
        JwtUserDetails userDetails = new JwtUserDetails(
                UUID.randomUUID(),
                randomAlphabetic(3, 10),
                randomAlphabetic(3, 10),
                Collections.emptyList()
        );
        return new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }
}

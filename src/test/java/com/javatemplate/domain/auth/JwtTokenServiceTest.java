package com.javatemplate.domain.auth;

import com.javatemplate.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

    private static final String SECRET = "testSecret";

    private static final Long EXPIRATION = 3600L;

    @InjectMocks
    private JwtTokenService jwtTokenService;

    @Mock
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        openMocks(this);
        jwtTokenService = new JwtTokenService(jwtProperties);
    }

    @Test
    public void shouldParseToken_OK() {
        final JwtUserDetails userDetails = new JwtUserDetails(UUID.randomUUID(), "user", "123123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(userDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        Authentication authentication = jwtTokenService.parse(token);

        assertNotNull(authentication);
        assertEquals(userDetails.getUserId(), authentication.getPrincipal());
        assertEquals(userDetails.getUsername(), authentication.getCredentials());
        assertEquals(claims.get("userId").toString(), authentication.getPrincipal().toString());
        assertEquals("ROLE_USER", claims.get("roles").toString());
    }

    @Test
    public void shouldParseTokenWithoutToken_ReturnNull() {
        Authentication authentication = jwtTokenService.parse(null);

        assertNull(authentication);
    }

    @Test
    public void shouldParseTokenWithoutUserId_ReturnNull() {
        final JwtUserDetails userDetails = new JwtUserDetails(null, "user", "123123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(userDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        assertNull(claims.get("userId"));
    }

    @Test
    public void generateToken_ShouldCreateValidToken() {
        final JwtUserDetails userDetails = new JwtUserDetails(UUID.randomUUID(), "user", "123123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(userDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        final Date expiration = claims.getExpiration();
        final Date issuedAt = claims.getIssuedAt();

        assertEquals("user", claims.getSubject());
        assertEquals("ROLE_USER", claims.get("roles").toString());
        assertTrue(expiration.toInstant().isAfter(issuedAt.toInstant()));
        assertEquals(expiration.getTime() - issuedAt.getTime(), EXPIRATION * 1000);
    }
}

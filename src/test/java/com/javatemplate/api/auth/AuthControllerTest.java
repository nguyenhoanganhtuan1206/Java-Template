package com.javatemplate.api.auth;

import com.javatemplate.api.AbstractControllerTest;
import com.javatemplate.domain.auth.JwtTokenService;
import com.javatemplate.domain.auth.JwtUserDetails;
import com.javatemplate.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

import static com.javatemplate.fakes.AuthFakes.buildAuth;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/auths";

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserService userService;

    @Test
    void shouldLogin_OK() throws Exception {
        final var auth = buildAuth();
        final var token = randomAlphabetic(3, 10);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(jwtTokenService.generateToken((JwtUserDetails) auth.getPrincipal())).thenReturn(token);

        post(BASE_URL, auth).andExpect(status().isOk()).andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void shouldLoginFacebookWithoutAccessToken_OK() {
        final JwtUserDetails userDetails = new JwtUserDetails(UUID.randomUUID(), "name", "email", List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));
        final TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder().accessToken(randomAlphabetic(3, 10)).build();
        final String jwtToken = randomAlphabetic(3, 10);

        when(userService.loginWithFacebook(tokenRequestDTO.getAccessToken())).thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn(jwtToken);

        final var jwtTokenActual = jwtTokenService.generateToken(userDetails);

        assertEquals(jwtToken, jwtTokenActual);
        assertNotNull(tokenRequestDTO);
    }

    @Test
    void shouldLoginFacebookWithoutAccessToken_ThroughBadRequest() throws Exception {
        final JwtUserDetails userDetails = new JwtUserDetails(UUID.randomUUID(), "name", "email", List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));
        final TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder().accessToken(randomAlphabetic(3, 10)).build();

        when(userService.loginWithFacebook(tokenRequestDTO.getAccessToken())).thenReturn(userDetails);

        post(BASE_URL + "/facebook", null).andExpect(status().isBadRequest());
    }
}

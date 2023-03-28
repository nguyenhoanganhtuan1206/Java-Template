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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.User;

import java.util.List;
import java.util.UUID;

import static com.javatemplate.fakes.AuthFakes.buildAuth;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    void shouldLoginFacebook_OK() throws Exception {
        final TokenRequestDTO tokenRequestDTO = new TokenRequestDTO("facebook-access-token");
        final UserDetails userDetails = new JwtUserDetails(UUID.randomUUID(), "test", "password", List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));
        final JwtTokenResponseDTO expectedResponseDTO = new JwtTokenResponseDTO("jwt-token");

        when(userService.loginWithFacebook(any(User.class))).thenReturn(userDetails);
        when(jwtTokenService.generateToken(any(JwtUserDetails.class))).thenReturn(expectedResponseDTO.getToken());

        // when
        post(BASE_URL + "/facebook", tokenRequestDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedResponseDTO.getToken()));

        // then
        verify(userService).loginWithFacebook(any(User.class));
        verify(jwtTokenService).generateToken(any(JwtUserDetails.class));
    }

    @Test
    void shouldLogin_OK() throws Exception {
        final var auth = buildAuth();
        final var token = randomAlphabetic(3, 10);

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(auth);
        when(jwtTokenService.generateToken((JwtUserDetails) auth.getPrincipal()))
                .thenReturn(token);

        post(BASE_URL, auth)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }
}

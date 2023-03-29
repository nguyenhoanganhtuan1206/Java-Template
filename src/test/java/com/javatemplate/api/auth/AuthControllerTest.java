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
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import java.util.List;
import java.util.UUID;

import static com.javatemplate.fakes.AuthFakes.buildAuth;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @MockBean
    private FacebookTemplate facebookTemplate;

    @MockBean
    private User user;

    @Test
    void shouldLoginFacebook_OK() throws Exception {
        final JwtUserDetails userDetails = new JwtUserDetails(UUID.randomUUID(), "test_name", "test_email", List.of(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")));
        final var tokenRequestDTO = TokenRequestDTO.builder().accessToken("EAAKcCqtX0rEBAKvG9rT7rTIzIr1J0ZCT4rsTkny5Dibq2echTZAddPJcrQkKf9yQDnvWkaHDBnnBvb1VuOchoA23HmeCtDPAILhfXDTyH8zCOaHHPblkLuLvnpdG46jZAZAblCZA0etL0WpbTt5VpIOaCWA3j4aiTd5CmFgPboSSD8oFq6Gi4OM3KXVnDLtxsCba1K3FW8OxuZBWfWqH5caJO3i6GNctYZD").build();

        final var jwtToken = randomAlphabetic(3, 10);
        final String[] fields = {"email", "name"};

        when(userService.loginWithFacebook(any(User.class))).thenReturn(userDetails);
        when(facebookTemplate.fetchObject(eq("me"), eq(User.class), eq(fields))).thenReturn(user);
        when(jwtTokenService.generateToken(userDetails)).thenReturn(jwtToken);

        post(BASE_URL + "/facebook", tokenRequestDTO.getAccessToken()).andExpect(status().isOk());

        verify(userService).loginWithFacebook(any(User.class));
        verify(jwtTokenService).generateToken(userDetails);
    }

    @Test
    void shouldLogin_OK() throws Exception {
        final var auth = buildAuth();
        final var token = randomAlphabetic(3, 10);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(jwtTokenService.generateToken((JwtUserDetails) auth.getPrincipal())).thenReturn(token);

        post(BASE_URL, auth).andExpect(status().isOk()).andExpect(jsonPath("$.token").value(token));
    }
}

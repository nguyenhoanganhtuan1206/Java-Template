package com.javatemplate.api.profile;

import com.javatemplate.api.AbstractControllerTest;
import com.javatemplate.api.WithMockAdmin;
import com.javatemplate.api.WithMockContributor;
import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.domain.user.User;
import com.javatemplate.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.javatemplate.fakes.UserFakes.buildUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc
class ProfileControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/profile";

    @MockBean
    private AuthsProvider authsProvider;

    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication()).thenCallRealMethod();
    }

    @Test
    @WithMockContributor
    void shouldGetProfile_OK() throws Exception {
        final var user = buildUser();
        final var userAuthenticationToken = authsProvider.getCurrentAuthentication();
        user.setId(userAuthenticationToken.getUserId());

        when(userService.findById(authsProvider.getCurrentUserId())).thenReturn(user);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userAuthenticationToken.getUserId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()));

        verify(userService).findById(authsProvider.getCurrentUserId());
    }

    @Test
    @WithMockAdmin
    void shouldGetProfileWithAdmin_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(authsProvider.getCurrentUserId())).thenReturn(user);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()));

        verify(userService).findById(authsProvider.getCurrentUserId());
    }

    @Test
    @WithMockContributor
    void shouldUpdate_Ok() throws Exception {
        final var updatedProfile = buildUser();
        final var userIdToken = authsProvider.getCurrentUserId();

        updatedProfile.setId(userIdToken);

        when(userService.update(eq(userIdToken), any(User.class))).thenReturn(updatedProfile);

        put(BASE_URL, updatedProfile)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedProfile.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedProfile.getLastName()))
                .andExpect(jsonPath("$.avatar").value(updatedProfile.getAvatar()));
    }
}

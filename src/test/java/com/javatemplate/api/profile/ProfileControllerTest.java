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

        when(userService.findById(userAuthenticationToken.getUserId()))
                .thenReturn(user);
        when(userService.findProfile())
                .thenReturn(user);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()));

        verify(userService).findProfile();
    }

    @Test
    @WithMockAdmin
    void shouldGetProfileWithAdmin_OK() throws Exception {
        final var user = buildUser();
        final var userAuthenticationToken = authsProvider.getCurrentAuthentication();

        when(userService.findById(userAuthenticationToken.getUserId()))
                .thenReturn(user);
        when(userService.findProfile())
                .thenReturn(user);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()));

        verify(userService).findProfile();
    }

    @Test
    @WithMockContributor
    void shouldUpdateWithContributor_OK() throws Exception {
        final var userToUpdate = buildUser();
        final var userIdToken = authsProvider.getCurrentAuthentication().getUserId();
        
        when(userService.findById(userIdToken))
                .thenReturn(userToUpdate);
        when(userService.update(eq(userIdToken), any(User.class)))
                .thenReturn(userToUpdate);
        when(userService.updateProfile(any(User.class)))
                .thenReturn(userToUpdate);

        put(BASE_URL, userToUpdate)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userToUpdate.getId().toString()))
                .andExpect(jsonPath("$.username").value(userToUpdate.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userToUpdate.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userToUpdate.getLastName()))
                .andExpect(jsonPath("$.avatar").value(userToUpdate.getAvatar()))
                .andExpect(jsonPath("$.enabled").value(userToUpdate.getEnabled()));
    }

    @Test
    @WithMockAdmin
    void shouldUpdateWithAdmin_OK() throws Exception {
        final var userToUpdate = buildUser();
        final var userIdToken = authsProvider.getCurrentAuthentication().getUserId();

        when(userService.findById(userIdToken))
                .thenReturn(userToUpdate);
        when(userService.update(eq(userIdToken), any(User.class)))
                .thenReturn(userToUpdate);
        when(userService.updateProfile(any(User.class)))
                .thenReturn(userToUpdate);

        put(BASE_URL, userToUpdate)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userToUpdate.getId().toString()))
                .andExpect(jsonPath("$.username").value(userToUpdate.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userToUpdate.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userToUpdate.getLastName()))
                .andExpect(jsonPath("$.avatar").value(userToUpdate.getAvatar()))
                .andExpect(jsonPath("$.enabled").value(userToUpdate.getEnabled()));
    }
}

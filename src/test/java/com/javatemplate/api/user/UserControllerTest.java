package com.javatemplate.api.user;

import com.javatemplate.api.AbstractControllerTest;
import com.javatemplate.api.WithMockAdmin;
import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.domain.user.User;
import com.javatemplate.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.javatemplate.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static com.javatemplate.fakes.UserFakes.buildUsers;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @MockBean
    private UserService userService;

    @MockBean
    private AuthsProvider authsProvider;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication()).thenReturn(buildAdmin());
    }

    @Test
    @WithMockAdmin
    void shouldFindAll_OK() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

        get(BASE_URL).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(users.size())).andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString())).andExpect(jsonPath("$[0].username").value(users.get(0).getUsername())).andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName())).andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName())).andExpect(jsonPath("$[0].enabled").value(users.get(0).getEnabled())).andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()));

        verify(userService).findAll();
    }

    @Test
    @WithMockAdmin
    void shouldFindById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        get(BASE_URL + "/" + user.getId()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(user.getId().toString())).andExpect(jsonPath("$.username").value(user.getUsername())).andExpect(jsonPath("$.firstName").value(user.getFirstName())).andExpect(jsonPath("$.lastName").value(user.getLastName())).andExpect(jsonPath("$.avatar").value(user.getAvatar()));

        verify(userService).findById(user.getId());
    }

    @Test
    @WithMockAdmin
    void shouldFindUsersByName_OK() throws Exception {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userService.findByName(anyString())).thenReturn(expected);

        final var actual = userService.findByName(user.getUsername());

        get(BASE_URL + "/search?name=" + user.getUsername()).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(actual.size())).andExpect(jsonPath("$[0].id").value(actual.get(0).getId().toString())).andExpect(jsonPath("$[0].username").value(actual.get(0).getUsername())).andExpect(jsonPath("$[0].firstName").value(actual.get(0).getFirstName())).andExpect(jsonPath("$[0].lastName").value(actual.get(0).getLastName())).andExpect(jsonPath("$[0].enabled").value(actual.get(0).getEnabled())).andExpect(jsonPath("$[0].avatar").value(actual.get(0).getAvatar()));
    }

    @Test
    @WithMockAdmin
    void shouldCreateUser_OK() throws Exception {
        final var user = buildUser();

        when(userService.create(any(User.class))).thenReturn(user);

        post(BASE_URL, user).andExpect(jsonPath("$.id").value(user.getId().toString())).andExpect(jsonPath("$.username").value(user.getUsername())).andExpect(jsonPath("$.firstName").value(user.getFirstName())).andExpect(jsonPath("$.lastName").value(user.getLastName())).andExpect(jsonPath("$.avatar").value(user.getAvatar())).andExpect(jsonPath("$.enabled").value(user.getEnabled()));
    }

    @Test
    @WithMockAdmin
    void shouldUpdateUser_OK() throws Exception {
        final var userToUpdate = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setId(userToUpdate.getId());

        when(userService.update(eq(userToUpdate.getId()), any(User.class))).thenReturn(userUpdate);

        put(BASE_URL + "/" + userToUpdate.getId(), userUpdate).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(userUpdate.getId().toString())).andExpect(jsonPath("$.username").value(userUpdate.getUsername())).andExpect(jsonPath("$.firstName").value(userUpdate.getFirstName())).andExpect(jsonPath("$.lastName").value(userUpdate.getLastName())).andExpect(jsonPath("$.avatar").value(userUpdate.getAvatar())).andExpect(jsonPath("$.enabled").value(userUpdate.getEnabled()));
    }

    @Test
    @WithMockAdmin
    void shouldDeleteById_OK() throws Exception {
        final var user = buildUser();

        delete(BASE_URL + "/" + user.getId()).andExpect(status().isOk());

        verify(userService).deleteById(user.getId());
    }
}

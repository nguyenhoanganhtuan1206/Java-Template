package com.javatemplate.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatemplate.domain.user.User;
import com.javatemplate.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javatemplate.api.user.UserDTOMapper.toUserDTO;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static com.javatemplate.fakes.UserFakes.buildUsers;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldFindAll_Ok() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(users.get(0).getEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(users.get(0).getRoleId().toString()));

        verify(userService).findAll();
    }

    @Test
    void shouldFindById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findById(user.getId());
    }

    @Test
    void shouldFindUsersByName_Ok() throws Exception {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userService.findUsersByName(anyString())).thenReturn(expected);

        final var actual = userService.findUsersByName(user.getUsername());

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/search?name=" + user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(actual.size()))
                .andExpect(jsonPath("$[0].id").value(actual.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(actual.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(actual.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(actual.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(actual.get(0).getEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(actual.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(actual.get(0).getRoleId().toString()));
    }

    @Test
    void shouldCreateUser_Ok() throws Exception {
        final var user = buildUser();

        when(userService.createUser(any(User.class))).thenReturn(user);

        this.mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()))
                .andExpect(jsonPath("$.enabled").value(user.getEnabled()));
    }

    @Test
    void shouldUpdateUser_Ok() throws Exception {
        final var userToUpdate = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setId(userToUpdate.getId());

        /* Use specific value for an argument */
        when(userService.updateUser(eq(userToUpdate.getId()), any(User.class)))
                .thenReturn(userUpdate);

        this.mvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/" + userToUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toUserDTO(userUpdate))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userUpdate.getId().toString()))
                .andExpect(jsonPath("$.username").value(userUpdate.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userUpdate.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userUpdate.getLastName()))
                .andExpect(jsonPath("$.avatar").value(userUpdate.getAvatar()))
                .andExpect(jsonPath("$.enabled").value(userUpdate.getEnabled()))
                .andExpect(jsonPath("$.roleId").value(userUpdate.getRoleId().toString()));
    }

    @Test
    void shouldDeleteById_Ok() throws Exception {
        final var user = buildUser();

        this.mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + user.getId()))
                .andExpect(status().isOk());

        verify(userService).deleteById(user.getId());
    }
}

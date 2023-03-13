package com.javatemplate.domain.user;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import com.javatemplate.persistent.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.fakes.UserFakes.buildUser;
import static com.javatemplate.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserStore userStore;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildUsers();

        when(userStore.findAll()).thenReturn(expected);

        final var actual = userService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).getEnabled(), actual.get(0).getEnabled());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());

        verify(userStore).findAll();
    }

    @Test
    void shouldCreateUser_OK() {
        final var user = buildUser();

        when(userStore.create(user)).thenReturn(user);

        final var userCreated = userService.create(user);

        assertEquals(user, userCreated);
        verify(userStore).create(user);
    }

    @Test
    void shouldCreateUser_ThrownBadRequest() {
        final var user = buildUser();
        user.setPassword(null);

        assertThrows(BadRequestException.class, () -> userService.create(user));
    }

    @Test
    void shouldCreateUser_ExistedWithUsername() {
        final var user = buildUser();

        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> userService.create(user));
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldFindById_OK() {
        final var expected = buildUser();

        when(userStore.findById(expected.getId())).thenReturn(Optional.of(expected));

        final var actual = userService.findById(expected.getId());

        assertEquals(expected, actual);

        verify(userStore).findById(expected.getId());
    }

    @Test
    void shouldFindById_ThrowNotFoundException() {
        final var uuid = randomUUID();

        when(userStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(uuid));
        verify(userStore).findById(uuid);
    }

    @Test
    void shouldUpdateUser_OK() {
        final var user = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setId(user.getId());
        userUpdate.setRoleId(user.getRoleId());

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.updateUser(user)).thenReturn(user);

        final var expected = userService.update(user.getId(), userUpdate);

        assertEquals(expected.getId().toString(), userUpdate.getId().toString());
        assertEquals(expected.getUsername(), userUpdate.getUsername());
        assertEquals(expected.getFirstName(), userUpdate.getFirstName());
        assertEquals(expected.getLastName(), userUpdate.getLastName());
        assertEquals(expected.getAvatar(), userUpdate.getAvatar());
        assertEquals(expected.getRoleId().toString(), userUpdate.getRoleId().toString());
        assertEquals(expected.getEnabled(), userUpdate.getEnabled());

        verify(userStore).updateUser(user);
    }

    @Test
    void shouldUpdateUser_ThrownLengthPasswordException() {
        final var user = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setPassword(randomAlphabetic(3, 5));

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> userService.update(user.getId(), userUpdate));

        verify(userStore).findById(user.getId());
    }

    @Test
    void shouldUpdateUser_NotFound() {
        final var userUpdate = buildUser();
        final var uuid = randomUUID();

        when(userStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(uuid, userUpdate));
        verify(userStore).findById(uuid);
    }

    @Test
    void shouldUpdateUser_UsernameExisted() {
        final var userToUpdate = buildUser();
        final var userExisted = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setUsername(userExisted.getUsername());

        when(userStore.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
        when(userStore.findByUsername(userUpdate.getUsername())).thenReturn(Optional.of(userUpdate));

        assertThrows(BadRequestException.class, () -> userService.update(userToUpdate.getId(), userUpdate));

        verify(userStore, never()).updateUser(userUpdate);
    }

    @Test
    void shouldDeleteById_OK() {
        final var user = buildUser();

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteById(user.getId());
        verify(userStore).deleteById(user.getId());
    }

    @Test
    void shouldDeleteById_NotFound() {
        final var uuid = UUID.randomUUID();

        when(userStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(uuid));
        verify(userStore).findById(uuid);
    }

    @Test
    void shouldFindByUsername_OK() {
        final var user = buildUser();

        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        final var actual = userService.findByUsername(user.getUsername());

        assertEquals(user, actual);
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldFindByUsername_ThrownNotFoundException() {
        final var username = randomAlphabetic(3, 10);

        when(userStore.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findByUsername(username));
        verify(userStore).findByUsername(username);
    }

    @Test
    void shouldFindByUsernameOrFirstNameOrLastName_OK() {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userStore.findByName(anyString())).thenReturn(expected);

        final var actual = userService.findByName(user.getUsername());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).getEnabled(), actual.get(0).getEnabled());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
    }

    @Test
    void shouldFindByUsernameOrFirstNameOrLastName_Empty() {
        final var username = randomAlphabetic(3, 10);

        when(userStore.findByName(username)).thenReturn(Collections.emptyList());

        final var actual = userService.findByName(username);

        assertTrue(actual.isEmpty());
        verify(userStore).findByName(username);
    }
}

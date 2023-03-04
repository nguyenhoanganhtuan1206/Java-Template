package com.javatemplate.domain.user;

import com.javatemplate.error.NotFoundException;
import com.javatemplate.error.UserExistedException;
import com.javatemplate.persistent.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javatemplate.fakes.UserFakes.buildUser;
import static com.javatemplate.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserStore userStore;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindAll_Ok() {
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
    void shouldCreateUser_Ok() {
        final var user = buildUser();

        when(userStore.createUser(user))
                .thenReturn(user);

        final var userCreated = userService.createUser(user);

        assertEquals(user, userCreated);
        verify(userStore).createUser(user);
    }

    @Test
    void shouldVerifyUserAvailable_Existed() {
        final var user = buildUser();

        when(userStore.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        assertThrows(UserExistedException.class, () -> userService.verifyUserAvailable(user));
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldVerifyUserAvailable_NotExisted() {
        final var username = randomAlphabetic(3, 10);
        final User newUser = User.builder().username(username).build();

        when(userStore.findByUsername(username))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userService.verifyUserAvailable(newUser));
    }

    @Test
    void shouldFindById_Ok() {
        final var expected = buildUser();

        when(userStore.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        assertEquals(expected, userService.findById(expected.getId()));

        verify(userStore).findById(expected.getId());
    }

    @Test
    void shouldFindById_Throw() {
        final var uuid = randomUUID();

        when(userStore.findById(uuid))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(uuid));
        verify(userStore).findById(uuid);
    }

    @Test
    void shouldUpdateUser_Ok() {
        final var user = buildUser();
        final var userUpdate = buildUser();

        /* This method will perform findById */
        when(userStore.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(userStore.updateUser(user))
                .thenReturn(user);

        final var expected = userService.updateUser(user.getId(), userUpdate);

        assertEquals(expected.getEnabled(), userUpdate.getEnabled());
        verify(userStore).updateUser(user);
    }

    @Test
    void shouldDeleteById_Ok() {
        final var user = buildUser();

        userService.deleteById(user.getId());
        verify(userStore).deleteById(user.getId());
    }
}

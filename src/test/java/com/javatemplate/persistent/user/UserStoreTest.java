package com.javatemplate.persistent.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.javatemplate.fakes.UserFakes.buildUserEntity;
import static com.javatemplate.fakes.UserFakes.builderUserEntities;
import static com.javatemplate.persistent.user.UserEntityMapper.toUser;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStoreTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserStore userStore;

    @Test
    void shouldFindAll_Ok() {
        final var expected = builderUserEntities();

        when(userRepository.findAll()).thenReturn(expected);

        assertEquals(expected.size(), userStore.findAll().size());

        verify(userRepository).findAll();
    }

    @Test
    void shouldFindById_Ok() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);

        when(userRepository.findById(user.getId())).thenReturn(userOpt);

        final var actual = userStore.findById(user.getId()).get();
        final var expected = userOpt.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getEnabled(), actual.getEnabled());
        assertEquals(expected.getRoleId(), actual.getRoleId());

        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldFindById_Empty() {
        final var id = randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertFalse(userRepository.findById(id).isPresent());
        verify(userRepository).findById(id);
    }

    @Test
    void shouldFindByUsername_Ok() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(userOpt);

        final var actual = userStore.findByUsername(user.getUsername()).get();
        final var expected = userOpt.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getEnabled(), actual.getEnabled());
        assertEquals(expected.getRoleId(), actual.getRoleId());

        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void shouldFindByUsername_Empty() {
        final var username = randomAlphabetic(3, 10);
        final Optional<UserEntity> userOpt = Optional.empty();

        when(userRepository.findByUsername(username)).thenReturn(userOpt);

        assertFalse(userRepository.findByUsername(username).isPresent());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldCreateUser_Ok() {
        final var expected = buildUserEntity();

        when(userRepository.save(any(UserEntity.class))).thenReturn(expected);

        final var actual = userStore.createUser(toUser(expected));

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getUsername(), expected.getUsername());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getAvatar(), expected.getAvatar());
        assertEquals(actual.getRoleId(), expected.getRoleId());
        assertEquals(actual.getEnabled(), expected.getEnabled());
    }

    @Test
    void shouldUpdateUser_Ok() {
        final var user = buildUserEntity();

        when(userRepository.save(any())).thenReturn(user);

        final var expected = userStore.updateUser(toUser(user));

        assertEquals(user.getId(), expected.getId());
        assertEquals(user.getUsername(), expected.getUsername());
        assertEquals(user.getFirstName(), expected.getFirstName());
        assertEquals(user.getLastName(), expected.getLastName());
        assertEquals(user.getAvatar(), expected.getAvatar());
        assertEquals(user.getRoleId(), expected.getRoleId());
        assertEquals(user.getEnabled(), expected.getEnabled());
    }

    @Test
    void shouldDeleteById_Ok() {
        final var user = buildUserEntity();

        userStore.deleteById(user.getId());

        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void shouldFindByUsernameOrFirstNameOrLastName_Ok() {
        final var user = buildUserEntity();
        final var expected = builderUserEntities();

        when(userRepository.findByUsernameOrFirstNameOrLastName(anyString()))
                .thenReturn(expected);

        final var actual = userStore.findUsersByName(user.getUsername());

        assertEquals(actual.size(), expected.size());

        verify(userRepository).findByUsernameOrFirstNameOrLastName(user.getUsername());
    }

    @Test
    void shouldFindByUsernameOrFirstNameOrLastName_Empty() {
        final var username = randomAlphabetic(3, 10);

        when(userRepository.findByUsernameOrFirstNameOrLastName(username))
                .thenReturn(Collections.emptyList());

        final var actual = userStore.findUsersByName(username);

        assertTrue(actual.isEmpty());

        verify(userRepository).findByUsernameOrFirstNameOrLastName(username);
    }
}

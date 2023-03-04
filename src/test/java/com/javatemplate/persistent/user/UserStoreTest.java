package com.javatemplate.persistent.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javatemplate.fakes.UserFakes.*;
import static com.javatemplate.persistent.user.UserEntityMapper.toUser;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        /* This line mean that `userOpt` will Optional of `user`
         * `userOpt` -> Optional<UserEntity> */
        final var userOpt = Optional.of(user);

        when(userRepository.findById(user.getId())).thenReturn(userOpt);

        assertEquals(userOpt, userRepository.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldFindById_Empty() {
        final var id = randomUUID();
        final Optional<UserEntity> userOpt = Optional.empty();

        when(userRepository.findById(id)).thenReturn(userOpt);

        assertFalse(userRepository.findById(id).isPresent());
        verify(userRepository).findById(id);
    }

    @Test
    void shouldFindByUsername_Ok() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(userOpt);

        assertEquals(userOpt, userRepository.findByUsername(user.getUsername()));
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void shouldFindByUsername_Empty() {
        final var username = randomAlphabetic(3, 10);
        final Optional<UserEntity> userOpt = Optional.empty();

        when(userRepository.findByUsername(username))
                .thenReturn(userOpt);

        assertFalse(userRepository.findByUsername(username).isPresent());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldCreateUser_Ok() {
        final var userEntity = buildUserEntity();
        final var user = buildUser();

        assertEquals(userStore.createUser(user), toUser(userEntity));
        verify(userRepository).save(userEntity);
    }

    @Test
    void shouldUpdateUser_Ok() {
    }

    @Test
    void shouldDeleteById_Ok() {
    }
}

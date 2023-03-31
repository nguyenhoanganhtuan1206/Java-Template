package com.javatemplate.domain.user;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import com.javatemplate.fakes.ITestCredentials;
import com.javatemplate.persistent.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.TestUser;
import org.springframework.social.facebook.api.TestUserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

import java.util.*;

import static com.javatemplate.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static com.javatemplate.fakes.UserFakes.buildUser;
import static com.javatemplate.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements ITestCredentials {

    @Mock
    private UserStore userStore;

    @InjectMocks
    private UserService userService;

    @Mock
    private AuthsProvider authsProvider;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private TestUser user;

    @Mock
    private TestUserOperations testUserOperations;

    @Test
    void shouldLoginWithFacebook_OK() {
        final OAuth2Operations oauth = new FacebookServiceProvider(APP_ID, APP_SECRET, null).getOAuthOperations();
        final AccessGrant clientGrant = oauth.authenticateClient();
        final FacebookTemplate facebookTemplate = new FacebookTemplate(clientGrant.getAccessToken(), "", APP_ID);
        final List<String> testUserIds = new ArrayList<>();

// Create a test user and add it to the list of test user IDs
        when(facebookTemplate.testUserOperations().createTestUser(anyBoolean(), anyString(), anyString()))
                .thenReturn(user);
        final TestUser testUser = facebookTemplate.testUserOperations().createTestUser(true, "publish_actions,read_stream,user_posts,user_tagged_places", "Alice Arensen");
        testUserIds.add(testUser.getId());

        when(userService.loginWithFacebook(testUser.getAccessToken())).thenReturn(mock(UserDetails.class));

// Delete the test user
        for (String testUserId : testUserIds) {
            testUserOperations.deleteTestUser(testUserId);
        }

    }

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
        assertEquals(passwordEncoder.encode(expected.get(0).getPassword()), passwordEncoder.encode(actual.get(0).getLastName()));
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

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.updateUser(user)).thenReturn(user);

        userUpdate.setId(user.getId());
        userUpdate.setRoleId(user.getRoleId());
        userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));

        final var actual = userService.update(user.getId(), userUpdate);

        assertEquals(userUpdate.getId(), actual.getId());
        assertEquals(userUpdate.getUsername(), actual.getUsername());
        assertEquals(userUpdate.getFirstName(), actual.getFirstName());
        assertEquals(userUpdate.getLastName(), actual.getLastName());
        assertEquals(userUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userUpdate.getRoleId(), actual.getRoleId());
        assertEquals(userUpdate.getEnabled(), actual.getEnabled());

        verify(userStore).updateUser(user);
    }

    @Test
    void shouldUpdateUserWithoutPassword_OK() {
        final var userToUpdate = buildUser();
        userToUpdate.setPassword(null);

        when(authsProvider.getCurrentUserId()).thenReturn(buildAdmin().getUserId());
        userToUpdate.setId(authsProvider.getCurrentUserId());

        when(userStore.updateUser(userToUpdate)).thenReturn(userToUpdate);
        when(userStore.findById(authsProvider.getCurrentUserId())).thenReturn(Optional.of(userToUpdate));

        final var actual = userService.update(authsProvider.getCurrentUserId(), userToUpdate);

        assertEquals(userToUpdate.getId().toString(), actual.getId().toString());
        assertEquals(userToUpdate.getUsername(), actual.getUsername());
        assertEquals(userToUpdate.getFirstName(), actual.getFirstName());
        assertEquals(userToUpdate.getLastName(), actual.getLastName());
        assertEquals(userToUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userToUpdate.getRoleId().toString(), actual.getRoleId().toString());
        assertEquals(userToUpdate.getEnabled(), actual.getEnabled());

        verify(userStore).updateUser(userToUpdate);
        verify(userStore).findById(authsProvider.getCurrentUserId());
    }

    @Test
    void shouldUpdateUserHaveSameUsername_OK() {
        final var user = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setUsername(user.getUsername());
        userUpdate.setRoleId(user.getRoleId());

        when(authsProvider.getCurrentUserId()).thenReturn(buildAdmin().getUserId());

        user.setId(authsProvider.getCurrentUserId());
        userUpdate.setId(authsProvider.getCurrentUserId());

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.updateUser(user)).thenReturn(user);

        final var actual = userService.update(user.getId(), userUpdate);

        assertEquals(userUpdate.getId().toString(), actual.getId().toString());
        assertEquals(userUpdate.getUsername(), actual.getUsername());
        assertEquals(userUpdate.getFirstName(), actual.getFirstName());
        assertEquals(userUpdate.getLastName(), actual.getLastName());
        assertEquals(userUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userUpdate.getRoleId().toString(), actual.getRoleId().toString());
        assertEquals(userUpdate.getEnabled(), actual.getEnabled());

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
    void shouldUpdateUser_ThroughAccessDeniedException() {
        final var userToUpdate = buildUser();
        final var userUpdate = buildUser();

        assertThrows(NotFoundException.class, () -> userService.update(userToUpdate.getId(), userUpdate));

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

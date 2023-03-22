package com.javatemplate.domain.user;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.api.user.UserValidation.validateUserCreate;
import static com.javatemplate.api.user.UserValidation.validateUserUpdate;
import static com.javatemplate.domain.user.UserError.supplyUserExisted;
import static com.javatemplate.domain.user.UserError.supplyUserNotFound;
import static com.javatemplate.error.CommonError.supplyAccessDeniedError;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    private final AuthsProvider authsProvider;

    private final PasswordEncoder passwordEncoder;

    private UUID getCurrentUserId() {
        return authsProvider.getCurrentAuthentication().getUserId();
    }

    private String getCurrentRole() {
        return authsProvider.getCurrentAuthentication().getRole();
    }

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User create(final User user) {
        validateUserCreate(user);

        verifyUsernameAvailable(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userStore.create(user);
    }

    public List<User> findByName(final String name) {
        return userStore.findByName(name);
    }

    public User findProfile() {
        return findById(getCurrentUserId());
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId).orElseThrow(supplyUserNotFound(userId));
    }

    public User findByUsername(final String username) {
        return userStore.findByUsername(username).orElseThrow(supplyUserNotFound(username));
    }

    public User updateProfile(final User userUpdate) {
        return update(getCurrentUserId(), userUpdate);
    }

    public User update(final UUID userId, final User userUpdate) {
        validateUserUpdatePermission(userId);
        final User user = findById(userId);

        validateUserUpdate(userUpdate);

        if (!(user.getUsername().equals(userUpdate.getUsername()))) {
            verifyUsernameAvailable(userUpdate.getUsername());

            user.setUsername(userUpdate.getUsername());
        }

        if (isNotBlank(userUpdate.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }

        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setAvatar(userUpdate.getAvatar());
        user.setEnabled(userUpdate.getEnabled());

        return userStore.updateUser(user);
    }

    public void deleteById(final UUID id) {
        final User user = findById(id);

        userStore.deleteById(user.getId());
    }

    private void validateUserUpdatePermission(final UUID userId) {
        if (getCurrentRole().equals("ROLE_CONTRIBUTOR") && !getCurrentUserId().equals(userId)) {
            throw supplyAccessDeniedError("You are not authorized to update this user").get();
        }
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);

        if (userOptional.isPresent()) {
            throw supplyUserExisted(username).get();
        }
    }
}

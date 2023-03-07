package com.javatemplate.domain.user;

import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.domain.user.UserError.supplyUserExisted;
import static com.javatemplate.domain.user.UserError.supplyUserNotFound;
import static com.javatemplate.error.CommonError.supplyValidationError;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User createUser(final User user) {
        validateUser(user);

        verifyUserAvailable(user.getUsername());

        return userStore.createUser(user);
    }

    public List<User> findUsersByName(final String name) {
        return userStore.findUsersByName(name);
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId).orElseThrow(supplyUserNotFound(userId));
    }

    public User findByName(final String username) {
        return userStore.findByUsername(username).orElseThrow(supplyUserExisted(username));
    }

    public User updateUser(final UUID userId, final User userUpdate) {
        validateUser(userUpdate);

        verifyUserAvailable(userUpdate.getUsername());

        final User user = findById(userId);

        user.setUsername(userUpdate.getUsername());
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

    private void validateUser(final User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw supplyValidationError("Request failed. Please check your inputs again").get();
        }
    }

    private void verifyUserAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);

        if (userOptional.isPresent()) {
            throw supplyUserExisted(username).get();
        }
    }
}

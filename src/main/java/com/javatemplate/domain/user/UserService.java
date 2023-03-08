package com.javatemplate.domain.user;

import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.api.user.UserValidation.validateUserCreate;
import static com.javatemplate.api.user.UserValidation.validateUserUpdate;
import static com.javatemplate.domain.user.UserError.supplyUserExisted;
import static com.javatemplate.domain.user.UserError.supplyUserNotFound;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User create(final User user) {
        validateUserCreate(user);

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
        return userStore.findByUsername(username).orElseThrow(supplyUserNotFound(username));
    }

    public User update(final UUID userId, final User userUpdate) {
        final User user = findById(userId);
        validateUserUpdate(userUpdate);

        verifyUserAvailableUpdate(user, userUpdate.getUsername());

        if (isNotBlank(userUpdate.getPassword())) {
            user.setPassword(userUpdate.getPassword());
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

    private void verifyUserAvailableUpdate(final User user, final String username) {
        if (!username.isEmpty() && !username.equals(user.getUsername())) {
            verifyUserAvailable(username);

            user.setUsername(username);
        }
    }

    private void verifyUserAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);

        if (userOptional.isPresent()) {
            throw supplyUserExisted(username).get();
        }
    }
}

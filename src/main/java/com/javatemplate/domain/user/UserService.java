package com.javatemplate.domain.user;

import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.domain.user.UserError.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User createUser(final User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw supplyInputsDataFailed().get();
        }

        verifyUserAvailable(user);

        return userStore.createUser(user);
    }

    public List<User> findUsersByName(final String name) {
        final List<User> users = userStore.findUsersByName(name);

        if (users.size() == 0) {
            throw supplyUserNotFound(name).get();
        }

        return userStore.findUsersByName(name);
    }

    public void verifyUserAvailable(final User user) {
        final Optional<User> userOptional = userStore.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            throw supplyUserExisted(user.getUsername()).get();
        }
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId).orElseThrow(supplyUserNotFound(userId));
    }

    public User findByName(final String username) {
        return userStore.findByUsername(username).orElseThrow(supplyUserExisted(username));
    }

    public User updateUser(final UUID userId, final User userUpdate) {
        if (userUpdate.getUsername() == null || userUpdate.getPassword() == null) {
            throw supplyInputsDataFailed().get();
        }

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
}

package com.javatemplate.domain.user;

import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.domain.user.UserError.supplyUserExisted;
import static com.javatemplate.domain.user.UserError.supplyUserNotFound;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User createUser(final User user) {
        verifyUserAvailable(user);

        return userStore.createUser(user);
    }

    public void verifyUserAvailable(final User user) {
        final Optional<User> userOptional = userStore.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            throw supplyUserExisted(user.getUsername()).get();
        }
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId)
                .orElseThrow(supplyUserNotFound(userId));
    }

    public User updateUser(final UUID userId, final User userUpdate) {
        final User user = findById(userId);

        user.setUsername(userUpdate.getUsername());
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setAvatar(userUpdate.getAvatar());
        user.setEnabled(userUpdate.getEnabled());

        return userStore.updateUser(user);
    }

    public void deleteById(final UUID id) {
        userStore.deleteById(id);
    }
}

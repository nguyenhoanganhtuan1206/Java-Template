package com.javatemplate.persistent.user;

import com.javatemplate.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.persistent.user.UserEntityMapper.*;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return toUsers(toList(userRepository.findAllByEnabledTrue()));
    }

    public Optional<User> findById(final UUID userId) {
        return userRepository.findById(userId).map(UserEntityMapper::toUser);
    }

    public List<User> findByName(final String name) {
        return toUsers(userRepository.findByName(name));
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsernameAndEnabledTrue(username).map(UserEntityMapper::toUser);
    }

    public User create(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public User updateUser(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public void delete(final User user) {
        user.setEnabled(false);

        userRepository.save(toUserEntity(user));
    }
}

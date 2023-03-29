package com.javatemplate.domain.user;

import com.javatemplate.domain.auth.JwtUserDetailsService;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    private final JwtUserDetailsService jwtUserDetailsService;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User create(final User user) {
        validateUserCreate(user);

        verifyUsernameAvailable(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userStore.create(user);
    }

    public UserDetails loginWithFacebook(final org.springframework.social.facebook.api.User userLogin) {
        final Optional<User> userFoundOptional = userStore.findByEmail(userLogin.getEmail());

        return jwtUserDetailsService.loadUserByUsername(userFoundOptional.get().getUsername());
    }

    public List<User> findByName(final String name) {
        return userStore.findByName(name);
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId).orElseThrow(supplyUserNotFound(userId));
    }

    public User findByUsername(final String username) {
        return userStore.findByUsername(username).orElseThrow(supplyUserNotFound(username));
    }

    public User update(final UUID userId, final User userUpdate) {
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

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);

        if (userOptional.isPresent()) {
            throw supplyUserExisted(username).get();
        }
    }
}

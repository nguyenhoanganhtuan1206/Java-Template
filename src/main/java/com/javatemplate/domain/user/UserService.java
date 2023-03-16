package com.javatemplate.domain.user;

import com.javatemplate.api.auth.UserAuthRequestDTO;
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
import static com.javatemplate.error.CommonError.supplyValidationError;
import static org.apache.commons.lang3.StringUtils.isBlank;
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

        verifyUsernameAvailable(user.getUsername());

        return userStore.create(user);
    }

    public User login(final UserAuthRequestDTO userAuthRequestDTO) {
        validateLoginRequest(userAuthRequestDTO);
        final User user = findByUsername(userAuthRequestDTO.getUsername());

        if (!user.getPassword().equals(userAuthRequestDTO.getPassword())) {
            throw supplyValidationError("Password is invalid, Please try again!").get();
        }

        return user;
    }

    public List<User> findByName(final String name) {
        return userStore.findByName(name);
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId)
                .orElseThrow(supplyUserNotFound(userId));
    }

    public User findByUsername(final String username) {
        return userStore.findByUsername(username).
                orElseThrow(supplyUserNotFound(username));
    }

    public User update(final UUID userId, final User userUpdate) {
        final User user = findById(userId);
        validateUserUpdate(userUpdate);

        if (!(user.getUsername().equals(userUpdate.getUsername()))) {
            verifyUsernameAvailable(userUpdate.getUsername());

            user.setUsername(userUpdate.getUsername());
        }

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

    private void validateLoginRequest(final UserAuthRequestDTO userAuthRequestDTO) {
        if (isBlank(userAuthRequestDTO.getUsername())) {
            throw supplyValidationError("Username cannot be empty").get();
        }

        if (isBlank(userAuthRequestDTO.getPassword())) {
            throw supplyValidationError("Password cannot be empty").get();
        }
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);

        if (userOptional.isPresent()) {
            throw supplyUserExisted(username).get();
        }
    }
}

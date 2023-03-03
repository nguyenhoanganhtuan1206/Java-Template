package com.javatemplate.domain.user;

import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User createUser(final User user) {
        // Find role by name in here
        return userStore.createUser(user);
    }
}

package com.javatemplate.persistent.user;

import com.javatemplate.domain.role.Role;
import com.javatemplate.domain.user.User;
import com.javatemplate.persistent.role.RoleStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.javatemplate.domain.role.RoleError.supplyRoleNotFound;
import static com.javatemplate.persistent.role.RoleEntityMapper.toRoleEntity;
import static com.javatemplate.persistent.user.UserEntityMapper.toUser;
import static com.javatemplate.persistent.user.UserEntityMapper.toUsers;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    private final RoleStore roleStore;

    public List<User> findAll() {
        return toUsers(toList(userRepository.findAll()));
    }

    public User createUser(final User user) {
        final Role role = roleStore.findById(user.getRoleId()).orElseThrow(supplyRoleNotFound(user.getRoleId()));

        final UserEntity userCreate = UserEntityMapper.toUserEntity(user);
        userCreate.setRole(toRoleEntity(role));

        return toUser(userRepository.save(userCreate));
    }
}

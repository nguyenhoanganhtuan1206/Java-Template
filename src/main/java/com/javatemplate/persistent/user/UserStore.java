package com.javatemplate.persistent.user;

import com.javatemplate.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.javatemplate.persistent.user.UserEntityMapper.toUsers;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return toUsers(toList(userRepository.findAll()));
    }
}

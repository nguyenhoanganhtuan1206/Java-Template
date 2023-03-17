package com.javatemplate.domain.auth;

import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserEntity;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.javatemplate.persistent.user.UserEntityMapper.toUserEntity;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username).map(user -> buildUser(toUserEntity(user))).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    private User buildUser(final UserEntity userEntity) {
        return new JwtUserDetails(userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                List.of(new SimpleGrantedAuthority(roleStore.findById(userEntity.getRoleId()).getName())));
    }
}
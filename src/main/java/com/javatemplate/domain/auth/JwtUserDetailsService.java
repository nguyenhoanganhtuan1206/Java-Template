package com.javatemplate.domain.auth;

import com.javatemplate.domain.role.Role;
import com.javatemplate.error.UsernameNotFoundException;
import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username)
                .map(this::buildUser)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private User buildUser(final com.javatemplate.domain.user.User user) {
        final Role role = roleStore.findById(user.getRoleId());

        return new JwtUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role.getName()))
        );
    }
}

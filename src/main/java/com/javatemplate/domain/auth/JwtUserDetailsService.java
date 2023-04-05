package com.javatemplate.domain.auth;

import com.javatemplate.error.UsernameNotFoundException;
import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.javatemplate.domain.auth.UserDetailsMapper.toUserDetails;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username)
                .map(user -> toUserDetails(user, roleStore.findById(user.getRoleId()).getName()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

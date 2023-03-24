package com.javatemplate.domain.auth;

import com.javatemplate.error.UsernameNotFoundException;
import com.javatemplate.persistent.role.RoleStore;
import com.javatemplate.persistent.user.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.javatemplate.fakes.RoleFakes.buildRole;
import static com.javatemplate.fakes.UserFakes.buildUserEntity;
import static com.javatemplate.persistent.user.UserEntityMapper.toUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class JwtUserDetailsServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private RoleStore roleStore;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        jwtUserDetailsService = new JwtUserDetailsService(userStore, roleStore);
    }

    @Test
    public void loadUserByUsername_whenUsernameNotFound_OK() {
        final var user = buildUserEntity();
        final var role = buildRole();
        role.setId(user.getRoleId());

        when(userStore.findByUsername(anyString())).thenReturn(Optional.of(toUser(user)));
        when(roleStore.findById(role.getId())).thenReturn(role);

        final UserDetails actual = jwtUserDetailsService.loadUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), actual.getUsername());
        verify(userStore).findByUsername(anyString());
        verify(roleStore).findById(role.getId());
    }

    @Test
    public void loadUserByUsername_whenUsernameNotFound_shouldThrowUsernameNotFoundException() {
        final var username = randomAlphabetic(3, 10);
        when(userStore.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(username));
        verify(userStore).findByUsername(anyString());
    }
}

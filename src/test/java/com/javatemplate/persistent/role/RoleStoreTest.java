package com.javatemplate.persistent.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javatemplate.fakes.RoleFakes.buildRoleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleStoreTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleStore roleStore;

    @Test
    void shouldFindById_OK() {
        final var role = buildRoleEntity();
        final var roleOptional = Optional.of(role);

        when(roleRepository.findById(role.getId()))
                .thenReturn(roleOptional);

        final var actual = roleStore.findById(role.getId());
        final var expected = roleOptional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

        verify(roleRepository).findById(role.getId());
    }
}

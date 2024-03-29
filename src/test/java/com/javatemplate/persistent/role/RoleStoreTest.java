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
        final var expected = Optional.of(role);

        when(roleRepository.findById(role.getId()))
                .thenReturn(expected);

        final var actual = roleStore.findById(role.getId());

        assertEquals(expected.get().getId(), actual.getId());
        assertEquals(expected.get().getName(), actual.getName());

        verify(roleRepository).findById(role.getId());
    }

    @Test
    void shouldFindByName_OK() {
        final var role = buildRoleEntity();
        final var expected = Optional.of(role);

        when(roleRepository.findByName(role.getName()))
                .thenReturn(expected);

        final var actual = roleStore.findByName(role.getName());


        assertEquals(expected.get().getId(), actual.getId());
        assertEquals(expected.get().getName(), actual.getName());

        verify(roleRepository).findByName(role.getName());
    }
}

package com.javatemplate.domain.role;

import com.javatemplate.persistent.role.RoleEntity;
import org.junit.jupiter.api.Test;

import static com.javatemplate.domain.role.RoleMapper.toRole;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleMapperTest {

    @Test
    void shouldToRole_OK() {
        final var roleEntity = RoleEntity.builder()
                .id(randomUUID())
                .name("ROLE_ADMIN")
                .build();
        final var role = toRole(roleEntity);

        assertEquals(roleEntity.getId(), role.getId());
        assertEquals(roleEntity.getName(), role.getName());
    }
}

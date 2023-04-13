package com.javatemplate.fakes;

import com.javatemplate.domain.role.Role;
import com.javatemplate.persistent.role.RoleEntity;
import lombok.experimental.UtilityClass;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class RoleFakes {

    public static RoleEntity buildRoleEntity() {
        return RoleEntity.builder()
                .id(randomUUID())
                .name(randomAlphabetic(3, 10))
                .build();
    }

    public static Role buildRole() {
        return Role.builder()
                .id(randomUUID())
                .name(randomAlphabetic(3, 10))
                .build();
    }
}

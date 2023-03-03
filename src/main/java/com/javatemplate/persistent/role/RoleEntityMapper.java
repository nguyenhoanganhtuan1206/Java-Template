package com.javatemplate.persistent.role;

import com.javatemplate.domain.role.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleEntityMapper {

    public static Role toRole(final RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }

    public static RoleEntity toRoleEntity(final Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}

package com.javatemplate.domain.role;

import com.javatemplate.persistent.role.RoleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public static Role toRole(final RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }
}

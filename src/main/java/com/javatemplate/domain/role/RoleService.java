package com.javatemplate.domain.role;

import com.javatemplate.persistent.role.RoleStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.javatemplate.domain.role.RoleError.supplyRoleNotFound;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleStore roleStore;

    public Role findById(final UUID roleId) {
        return roleStore.findById(roleId)
                .orElseThrow(supplyRoleNotFound(roleId));
    }
}

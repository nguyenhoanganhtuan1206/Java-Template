package com.javatemplate.persistent.role;

import com.javatemplate.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public Optional<Role> findById(final UUID roleId) {
        return roleRepository.findById(roleId)
                .map(RoleEntityMapper::toRole);
    }
}

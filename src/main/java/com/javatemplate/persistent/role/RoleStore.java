package com.javatemplate.persistent.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public String findById(final UUID id) {
        return roleRepository.findById(id).get().getName();
    }
}

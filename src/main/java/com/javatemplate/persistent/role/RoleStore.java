package com.javatemplate.persistent.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;
}

package com.javatemplate.domain.role;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class Role {

    private UUID id;

    private String name;
}

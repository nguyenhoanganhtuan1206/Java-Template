package com.javatemplate.domain.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class Role {

    private UUID id;

    private String name;
}

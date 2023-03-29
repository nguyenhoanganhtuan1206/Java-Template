package com.javatemplate.api.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponseDTO {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    private String avatar;

    private UUID roleId;
}

package com.javatemplate.api.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UserAuthResponseDTO {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    private String avatar;

    private UUID roleId;
}

package com.javatemplate.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class User {

    private UUID id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    private String avatar;

    private UUID roleId;
}

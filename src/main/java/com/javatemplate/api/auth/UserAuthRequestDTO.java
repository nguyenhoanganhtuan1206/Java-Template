package com.javatemplate.api.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAuthRequestDTO {

    private String username;

    private String password;
}

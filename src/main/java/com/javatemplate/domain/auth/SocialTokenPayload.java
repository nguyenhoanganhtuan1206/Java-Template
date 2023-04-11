package com.javatemplate.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocialTokenPayload {

    private String username;

    private String email;

    private String name;

    private String firstName;

    private String lastName;
}

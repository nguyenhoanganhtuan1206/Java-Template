package com.javatemplate.api.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenResponseDTO {

    private final String token;
}

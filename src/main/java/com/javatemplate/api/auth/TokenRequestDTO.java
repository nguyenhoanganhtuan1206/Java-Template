package com.javatemplate.api.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRequestDTO {

    private String accessToken;
}

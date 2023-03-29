package com.javatemplate.api.auth;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TokenRequestDTO {

    private String accessToken;
}

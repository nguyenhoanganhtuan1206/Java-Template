package com.javatemplate.api.auth;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TokenRequestDTO {

    private String accessToken;
}

package com.javatemplate.api.auth;

import com.javatemplate.domain.auth.AuthsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.javatemplate.api.auth.UserProfileDTOMapper.toUserProfileDTO;

@RestController
@RequestMapping("/api/v1/test-auths")
@RequiredArgsConstructor
public class TestAuthController {

    private final AuthsProvider authsProvider;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("users")
    public UserProfileDTO testUser() {
        return toUserProfileDTO(authsProvider.getCurrentAuthentication());
    }
}

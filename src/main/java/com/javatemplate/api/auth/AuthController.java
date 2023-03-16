package com.javatemplate.api.auth;

import com.javatemplate.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.javatemplate.api.auth.UserAuthDTOMapper.toUserAuthDTO;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping
    public UserAuthResponseDTO login(final @RequestBody UserAuthRequestDTO userAuthRequestDTO) {
        return toUserAuthDTO(userService.login(userAuthRequestDTO));
    }
}

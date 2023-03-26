package com.javatemplate.api.auth;

import com.javatemplate.domain.auth.JwtTokenService;
import com.javatemplate.domain.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.javatemplate.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public JwtTokenResponseDTO login(final @RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenService.generateToken((JwtUserDetails) authentication.getPrincipal()))
                .build();
    }

    @PostMapping("/facebook")
    public JwtTokenResponseDTO loginWithFacebook(@RequestParam("accessToken") String accessToken) {
        System.out.println(accessToken);
        return null;
    }
}

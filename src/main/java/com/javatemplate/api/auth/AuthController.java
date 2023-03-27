package com.javatemplate.api.auth;

import com.javatemplate.domain.auth.JwtTokenService;
import com.javatemplate.domain.auth.JwtUserDetails;
import com.javatemplate.domain.auth.JwtUserDetailsService;
import com.javatemplate.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.javatemplate.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final UserService userService;

    @PostMapping
    public JwtTokenResponseDTO login(final @RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenService.generateToken((JwtUserDetails) authentication.getPrincipal()))
                .build();
    }

    @PostMapping("/facebook")
    public JwtTokenResponseDTO loginWithFacebook(@RequestBody TokenRequestDTO tokenRequestDTO) {
        final Facebook facebook = new FacebookTemplate(tokenRequestDTO.getAccessToken());

        final String[] fields = {"email", "name"};
        final User user = facebook.fetchObject("me", User.class, fields);
        final com.javatemplate.domain.user.User userCreated = userService.loginWithFacebook(user.getEmail(), user.getName());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userCreated.getUsername());

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenService.generateToken((JwtUserDetails) userDetails))
                .build();
    }
}

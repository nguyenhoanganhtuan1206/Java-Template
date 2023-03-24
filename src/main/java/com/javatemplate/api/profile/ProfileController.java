package com.javatemplate.api.profile;

import com.javatemplate.api.user.UserRequestDTO;
import com.javatemplate.api.user.UserResponseDTO;
import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.javatemplate.api.user.UserDTOMapper.toUserDTO;
import static com.javatemplate.domain.user.UserMapper.toUser;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CONTRIBUTOR', 'ADMIN')")
public class ProfileController {

    private final UserService userService;

    private final AuthsProvider authsProvider;

    @Operation(summary = "Update current user's profile")
    @PutMapping
    public UserResponseDTO update(final @RequestBody UserRequestDTO userRequestDTO) {
        return toUserDTO(userService.update(authsProvider.getCurrentUserId(), toUser(userRequestDTO)));
    }

    @Operation(summary = "Get the current user's profile")
    @GetMapping
    public UserResponseDTO getProfile() {
        return toUserDTO(userService.findById(authsProvider.getCurrentUserId()));
    }
}

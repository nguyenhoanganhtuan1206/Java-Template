package com.javatemplate.api.profile;

import com.javatemplate.api.user.UserRequestDTO;
import com.javatemplate.api.user.UserResponseDTO;
import com.javatemplate.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.javatemplate.api.user.UserDTOMapper.toUserDTO;
import static com.javatemplate.domain.user.UserMapper.toUser;

@RestController
@RequestMapping("api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('CONTRIBUTOR','ADMIN')")
    @Operation(summary = "Get the current user's information")
    @GetMapping("{userId}")
    public UserResponseDTO findById(final @PathVariable UUID userId) {
        return toUserDTO(userService.findById(userId));
    }

    @PreAuthorize("hasAnyRole('CONTRIBUTOR','ADMIN')")
    @Operation(summary = "Get the current user's information")
    @GetMapping("{userId}")
    public UserResponseDTO update(
            final @PathVariable UUID userId,
            final @RequestBody UserRequestDTO userRequestDTO
    ) {
        return toUserDTO(userService.update(userId, toUser(userRequestDTO)));
    }
}

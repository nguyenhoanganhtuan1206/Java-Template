package com.javatemplate.api.user;

import com.javatemplate.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.javatemplate.api.user.UserDTOMapper.toUserDTO;
import static com.javatemplate.api.user.UserDTOMapper.toUsersDTO;
import static com.javatemplate.domain.user.UserMapper.toUser;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return toUsersDTO(userService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Find user by id")
    @GetMapping("{userId}")
    public UserResponseDTO findById(final @PathVariable UUID userId) {
        return toUserDTO(userService.findById(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Find user by name")
    @GetMapping("/search")
    public List<UserResponseDTO> findByName(final @RequestParam String name) {
        return toUsersDTO(userService.findByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create user")
    @PostMapping
    public UserResponseDTO create(final @RequestBody UserRequestDTO userDTO) {
        return toUserDTO(userService.create(toUser(userDTO)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user")
    @PutMapping("{userId}")
    public UserResponseDTO update(final @RequestBody UserRequestDTO userDTO, final @PathVariable UUID userId) {
        return toUserDTO(userService.update(userId, toUser(userDTO)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by Id")
    @DeleteMapping("{userId}")
    public void deleteById(final @PathVariable UUID userId) {
        userService.deleteById(userId);
    }
}

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
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return toUsersDTO(userService.findAll());
    }

    @Operation(summary = "Find user by id")
    @GetMapping("{userId}")
    public UserResponseDTO findById(final @PathVariable UUID userId) {
        return toUserDTO(userService.findById(userId));
    }

    @Operation(summary = "Find user by name")
    @GetMapping("/search")
    public List<UserResponseDTO> findByName(final @RequestParam String name) {
        return toUsersDTO(userService.findByName(name));
    }

    @Operation(summary = "Create user")
    @PostMapping
    public UserResponseDTO create(final @RequestBody UserRequestDTO userDTO) {
        return toUserDTO(userService.create(toUser(userDTO)));
    }

    @Operation(summary = "Update user")
    @PutMapping("{userId}")
    public UserResponseDTO update(final @RequestBody UserRequestDTO userDTO, final @PathVariable UUID userId) {
        return toUserDTO(userService.update(userId, toUser(userDTO)));
    }

    @Operation(summary = "Delete user by Id")
    @DeleteMapping("{userId}")
    public UserResponseDTO deleteById(final @PathVariable UUID userId) {
        return toUserDTO(userService.deleteById(userId));
    }
}

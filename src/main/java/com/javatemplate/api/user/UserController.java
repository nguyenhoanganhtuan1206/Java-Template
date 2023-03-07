package com.javatemplate.api.user;

import com.javatemplate.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserDTOResponse> findAll() {
        return toUsersDTO(userService.findAll());
    }

    @Operation(summary = "Find user by id")
    @GetMapping("/{userId}")
    public UserDTOResponse findById(final @PathVariable UUID userId) {
        return toUserDTO(userService.findById(userId));
    }

    @Operation(summary = "Find user by name")
    @GetMapping("/search")
    public List<UserDTOResponse> findByName(final @RequestParam String name) {
        return toUsersDTO(userService.findUsersByName(name));
    }

    @Operation(summary = "Create user")
    @PostMapping
    public UserDTOResponse createUser(final @RequestBody UserDTORequest userDTO) {
        return toUserDTO(userService.create(toUser(userDTO)));
    }

    @Operation(summary = "Update user")
    @PatchMapping("/{userId}")
    public UserDTOResponse update(final @RequestBody UserDTORequest userDTO, final @PathVariable UUID userId) {
        return toUserDTO(userService.update(userId, toUser(userDTO)));
    }

    @Operation(summary = "Delete user by Id")
    @DeleteMapping("/{userId}")
    public void deleteById(final @PathVariable UUID userId) {
        userService.deleteById(userId);
    }
}

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
    public List<UserDTO> findAll() {
        return toUsersDTO(userService.findAll());
    }

    @Operation(summary = "Find user by id")
    @GetMapping("/{userId}")
    public UserDTO findById(final @PathVariable UUID userId) {
        return toUserDTO(userService.findById(userId));
    }

    @Operation(summary = "Find user by name")
    @GetMapping("/search")
    public List<UserDTO> findByName(final @RequestParam String name) {
        return toUsersDTO(userService.findByUsernameOrFirstNameOrLastName(name));
    }

    @Operation(summary = "Create user")
    @PostMapping
    public UserDTO createUser(final @RequestBody UserDTO userDTO) {
        return toUserDTO(userService.createUser(toUser(userDTO)));
    }

    @Operation(summary = "Update user")
    @PatchMapping("/{userId}")
    public UserDTO update(final @RequestBody UserDTO userDTO, final @PathVariable UUID userId) {
        return toUserDTO(userService.updateUser(userId, toUser(userDTO)));
    }

    @Operation(summary = "Delete user by Id")
    @DeleteMapping("/{userId}")
    public void deleteById(final @PathVariable UUID userId) {
        userService.deleteById(userId);
    }
}

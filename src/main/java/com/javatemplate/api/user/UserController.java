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

    @Operation(summary = "Create user")
    @PostMapping("/create")
    public UserDTO createUser(final @RequestBody UserDTO userDTO) {
        return toUserDTO(userService.createUser(toUser(userDTO)));
    }

    @Operation(summary = "Update user")
    @PatchMapping("/update/{userId}")
    public UserDTO updateUser(final @RequestBody UserDTO userDTO,
                              final @PathVariable UUID userId) {
        return toUserDTO(userService.updateUser(userId, toUser(userDTO)));
    }

    @Operation(summary = "Delete user by Id")
    @DeleteMapping("/delete/{userId}")
    public UserDTO deleteById(final @PathVariable UUID userId) {
        final UserDTO userDelete = toUserDTO(userService.findById(userId));

        userService.deleteById(userId);

        return userDelete;
    }
}

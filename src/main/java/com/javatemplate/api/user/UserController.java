package com.javatemplate.api.user;

import com.javatemplate.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.javatemplate.api.user.UserDTOMapper.toUsersDTO;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> findAll() {
        return toUsersDTO(userService.findAll());
    }

    @PostMapping
    public UserDTO createUser(final @RequestBody UserDTO userDTO) {

        return null;
    }
}

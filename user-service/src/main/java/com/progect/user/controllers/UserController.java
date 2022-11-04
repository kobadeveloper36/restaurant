package com.progect.user.controllers;

import com.progect.user.controllers.dto.UserRequestDTO;
import com.progect.user.controllers.dto.UserResponseDTO;
import com.progect.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable Long userId) {
        return new UserResponseDTO(userService.getUserById(userId));
    }

    @PostMapping("/")
    public Long createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    @PutMapping("/{userId}")
    public UserResponseDTO updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(userId, userRequestDTO);
    }

    @DeleteMapping("/{userId}")
    public UserResponseDTO deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}

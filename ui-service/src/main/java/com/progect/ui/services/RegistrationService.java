package com.progect.ui.services;

import com.progect.ui.rest.dto.user.UserRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userService.createUser(userRequestDTO);
    }

    public void register(Long userId, UserRequestDTO userRequestDTO) {
        String password = userRequestDTO.getPassword();
        if (password.equals("")) {
            password = userService.getUserById(userId).getPassword();
            userRequestDTO.setPassword(password);
            userService.updateUser(userId, userRequestDTO);
        } else {
            userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            userService.updateUser(userId, userRequestDTO);
        }
    }
}

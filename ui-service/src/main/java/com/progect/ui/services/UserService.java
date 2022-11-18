package com.progect.ui.services;

import com.progect.ui.rest.UserServiceClient;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserServiceClient userServiceClient;

    public UserService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public UserResponseDTO getUserById(Long userId) {
        return userServiceClient.getUserById(userId);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userServiceClient.getAllUsers();
    }
}

package com.progect.user.services;

import com.progect.user.controllers.dto.UserRequestDTO;
import com.progect.user.controllers.dto.UserResponseDTO;
import com.progect.user.entities.User;
import com.progect.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with id: " + userId + "not found"));
    }

    public Long createUser(UserRequestDTO userRequestDTO) {
        User newUser = new User(userRequestDTO.getName(), userRequestDTO.getPhone(),
                userRequestDTO.getEmail(), userRequestDTO.getAddress(),
                userRequestDTO.getFlat(), userRequestDTO.getEntry(), userRequestDTO.getFloor(),
                userRequestDTO.getLogin(), userRequestDTO.getPassword(), userRequestDTO.getComments(),
                userRequestDTO.getOrders(), userRequestDTO.getPrivileges());
        return userRepository.save(newUser).getUserId();
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUserId(userId);
        user.setName(userRequestDTO.getName());
        user.setPhone(userRequestDTO.getPhone());
        user.setEmail(userRequestDTO.getEmail());
        user.setAddress(userRequestDTO.getAddress());
        user.setFlat(userRequestDTO.getFlat());
        user.setEntry(userRequestDTO.getEntry());
        user.setFloor(userRequestDTO.getFloor());
        user.setLogin(userRequestDTO.getLogin());
        user.setPassword(userRequestDTO.getPassword());
        user.setComments(userRequestDTO.getComments());
        user.setOrders(userRequestDTO.getOrders());
        user.setPrivileges(userRequestDTO.getPrivileges());
        return new UserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO deleteUser(Long userId) {
        User userById = getUserById(userId);
        userRepository.deleteById(userId);
        return new UserResponseDTO(userById);
    }
}

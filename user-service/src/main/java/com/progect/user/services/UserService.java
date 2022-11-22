package com.progect.user.services;

import com.progect.user.controllers.dto.UserRequestDTO;
import com.progect.user.controllers.dto.UserResponseDTO;
import com.progect.user.entities.User;
import com.progect.user.entities.UsersRoles;
import com.progect.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with id: " + userId + "not found"));
    }

    public Long createUser(UserRequestDTO userRequestDTO) {
        String userRole = userRequestDTO.getRole();
        if (Arrays.stream(UsersRoles.values()).anyMatch(x -> x.name().equals(userRole))) {
            User newUser = new User(userRequestDTO.getName(), userRequestDTO.getPhone(),
                    userRequestDTO.getEmail(), userRequestDTO.getAddress(),
                    userRequestDTO.getFlat(), userRequestDTO.getEntry(), userRequestDTO.getFloor(),
                    userRequestDTO.getLogin(), userRequestDTO.getPassword(), userRequestDTO.getComments(),
                    userRequestDTO.getOrders(), userRole, userRequestDTO.getImgFile());
            return userRepository.save(newUser).getUserId();
        } else {
            throw new IllegalArgumentException("Role not found");
        }
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        String userRole = userRequestDTO.getRole();
        if (Arrays.stream(UsersRoles.values()).anyMatch(x -> x.name().equals(userRole))) {
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
            user.setRole(userRole);
            user.setImgFile(userRequestDTO.getImgFile());
            return new UserResponseDTO(userRepository.save(user));
        } else {
            throw new IllegalArgumentException("Role not found");
        }
    }

    public UserResponseDTO deleteUser(Long userId) {
        User userById = getUserById(userId);
        userRepository.deleteById(userId);
        return new UserResponseDTO(userById);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    public UserResponseDTO getUserByLogin(String userLogin) {
        return new UserResponseDTO(userRepository.findUserByLogin(userLogin));
    }
}

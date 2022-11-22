package com.progect.ui.services;

import com.progect.ui.rest.UserServiceClient;
import com.progect.ui.rest.dto.user.UserRequestDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.rest.dto.user.UsersRoles;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Long createUser(UserRequestDTO userRequestDTO) {
        return userServiceClient.createUser(userRequestDTO);
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        return userServiceClient.updateUserById(userId, userRequestDTO);
    }

    public UserResponseDTO deleteUserById(Long userId) {
        return userServiceClient.deleteUserById(userId);
    }

    public Long setOrderId(Long userId, Long orderId) {
        UserResponseDTO userResponseDTO = getUserById(userId);
        String userRole = userResponseDTO.getRole();
        if (Arrays.stream(UsersRoles.values()).anyMatch(x -> x.name().equals(userRole))) {
            UserRequestDTO user = new UserRequestDTO();
            user.setName(userResponseDTO.getName());
            user.setPhone(userResponseDTO.getPhone());
            user.setEmail(userResponseDTO.getEmail());
            user.setAddress(userResponseDTO.getAddress());
            user.setFlat(userResponseDTO.getFlat());
            user.setEntry(userResponseDTO.getEntry());
            user.setFloor(userResponseDTO.getFloor());
            user.setLogin(userResponseDTO.getLogin());
            user.setPassword(userResponseDTO.getPassword());
            user.setComments(userResponseDTO.getComments());
            List<Long> userOrders = user.getOrders();
            if (userOrders == null) {
                userOrders = new ArrayList<>();
            }
            userOrders.add(orderId);
            user.setRole(userRole);
            user.setImgFile(userResponseDTO.getImgFile());
            return userId;
        }
        throw new IllegalArgumentException("Role not found");
    }

    public String getSetCommentId(String login, Long commentId) {
        UserResponseDTO userResponseDTO = getUserByLogin(login);
        String userRole = userResponseDTO.getRole();
        if (Arrays.stream(UsersRoles.values()).anyMatch(x -> x.name().equals(userRole))) {
            UserRequestDTO user = new UserRequestDTO();
            user.setName(userResponseDTO.getName());
            user.setPhone(userResponseDTO.getPhone());
            user.setEmail(userResponseDTO.getEmail());
            user.setAddress(userResponseDTO.getAddress());
            user.setFlat(userResponseDTO.getFlat());
            user.setEntry(userResponseDTO.getEntry());
            user.setFloor(userResponseDTO.getFloor());
            user.setLogin(userResponseDTO.getLogin());
            user.setPassword(userResponseDTO.getPassword());
            List<Long> userComments = user.getOrders();
            if (userComments == null) {
                userComments = new ArrayList<>();
            }
            userComments.add(commentId);
            user.setOrders(userResponseDTO.getOrders());
            user.setRole(userRole);
            user.setImgFile(userResponseDTO.getImgFile());
            return login;
        }
        throw new IllegalArgumentException("Role not found");
    }

    public UserResponseDTO getUserByLogin(String login) {
        return userServiceClient.getUserByLogin(login);
    }
}

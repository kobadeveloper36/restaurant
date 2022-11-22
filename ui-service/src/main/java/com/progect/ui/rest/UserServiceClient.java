package com.progect.ui.rest;

import com.progect.ui.rest.dto.user.UserRequestDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @RequestMapping(value = "users/{userId}", method = RequestMethod.GET)
    UserResponseDTO getUserById(@PathVariable Long userId);

    @RequestMapping(value = "users/", method = RequestMethod.POST)
    Long createUser(UserRequestDTO userRequestDTO);

    @RequestMapping(value = "users/{userId}", method = RequestMethod.PUT)
    UserResponseDTO updateUserById(@PathVariable Long userId, UserRequestDTO userRequestDTO);

    @RequestMapping(value = "users/{userId}", method = RequestMethod.DELETE)
    UserResponseDTO deleteUserById(@PathVariable Long userId);

    @GetMapping("users/")
    List<UserResponseDTO> getAllUsers();

    @GetMapping("users/login/{userLogin}")
    UserResponseDTO getUserByLogin(@PathVariable String userLogin);
}

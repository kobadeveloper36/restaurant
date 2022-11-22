package com.progect.user.controllers.dto;

import com.progect.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String flat;
    private String entry;
    private String floor;
    private String login;
    private String password;
    private List<Long> orders;
    private List<Long> comments;
    private String role;

    private String imgFile;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.flat = user.getFlat();
        this.entry = user.getEntry();
        this.floor = user.getFloor();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.orders = user.getOrders();
        this.comments = user.getComments();
        this.role = user.getRole();
        this.imgFile = user.getImgFile();
    }
}

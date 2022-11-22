package com.progect.user.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserRequestDTO {
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
}

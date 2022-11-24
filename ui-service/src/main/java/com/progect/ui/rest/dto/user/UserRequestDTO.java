package com.progect.ui.rest.dto.user;

import com.progect.ui.security.UsersRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
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

    public UserRequestDTO(String login, String password, String email) {
        this.name = "Не вказано";
        this.phone = "Не вказано";
        this.email = email;
        this.address = "Не вказано";
        this.flat = "Не вказано";
        this.entry = "Не вказано";
        this.floor = "Не вказано";
        this.imgFile = "user.png";
        this.role = UsersRoles.USER.name();
        this.login = login;
        this.password = password;
    }
}

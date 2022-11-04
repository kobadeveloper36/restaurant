package com.progect.user.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "flat", nullable = false)
    private String flat;

    @Column(name = "entry", nullable = false)
    private String entry;

    @Column(name = "floor", nullable = false)
    private String floor;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection
    @Column(name = "comment_id")
    @CollectionTable(name = "user_comments", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> comments = new ArrayList<>();

    @ElementCollection
    @Column(name = "order_id")
    @CollectionTable(name = "user_orders", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> orders = new ArrayList<>();

    @Column(name = "privileges", nullable = false)
    private String privileges;

    public User(String name, String phone, String email, String address,
                String flat, String entry, String floor, String login,
                String password, List<Long> comments,
                List<Long> orders, String privileges) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.flat = flat;
        this.entry = entry;
        this.floor = floor;
        this.login = login;
        this.password = password;
        this.comments = comments;
        this.orders = orders;
        this.privileges = privileges;
    }
}

package com.progect.user.repository;

import com.progect.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByLogin(String login);
}
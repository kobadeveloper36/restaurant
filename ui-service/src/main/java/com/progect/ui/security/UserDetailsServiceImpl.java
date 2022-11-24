package com.progect.ui.security;

import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponseDTO user = userService.getUserByLogin(username);
        if (user != null) {
            return new UserDetailsImpl(user);
        }
        throw new UsernameNotFoundException("Username not found: " + username);
    }
}

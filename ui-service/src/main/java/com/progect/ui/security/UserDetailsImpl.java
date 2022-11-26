package com.progect.ui.security;

import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.rest.dto.user.UsersRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private UserResponseDTO userResponseDTO;

    public UserDetailsImpl(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(UsersRoles.valueOf(userResponseDTO.getRole()));
    }

    @Override
    public String getPassword() {
        return userResponseDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userResponseDTO.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserResponseDTO getUserResponseDTO() {
        return this.userResponseDTO;
    }

    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }
}

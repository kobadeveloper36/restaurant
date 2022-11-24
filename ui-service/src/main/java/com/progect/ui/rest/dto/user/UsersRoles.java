package com.progect.ui.rest.dto.user;

import org.springframework.security.core.GrantedAuthority;

public enum UsersRoles implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

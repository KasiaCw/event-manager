package com.zdjavapol110.eventmanager.core.modules.user;

import lombok.Getter;
import lombok.Setter;

@Setter
public class UserRole {

    private String role;

    public UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}

package com.example.polls.payload.user;

import com.example.polls.payload.role.RoleBaseDto;

import java.util.Set;

public class UserDto extends UserBaseDto {
    private String password;

    private Set<RoleBaseDto> roles;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleBaseDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBaseDto> roles) {
        this.roles = roles;
    }
}

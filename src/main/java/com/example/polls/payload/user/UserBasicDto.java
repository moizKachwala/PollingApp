package com.example.polls.payload.user;

import com.example.polls.payload.role.RoleBaseDto;

import java.util.Set;

public class UserBasicDto extends UserBaseDto {

    private Set<RoleBaseDto> roles;

    public Set<RoleBaseDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBaseDto> roles) {
        this.roles = roles;
    }
}

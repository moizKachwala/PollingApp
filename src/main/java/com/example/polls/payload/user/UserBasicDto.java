package com.example.polls.payload.user;

import com.example.polls.payload.role.RoleBasicDto;

import java.util.Set;

public class UserBasicDto extends UserBaseDto {

    private Set<RoleBasicDto> roles;

    public Set<RoleBasicDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBasicDto> roles) {
        this.roles = roles;
    }
}

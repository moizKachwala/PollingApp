package com.example.polls.payload.user;

import com.example.polls.payload.role.PermissionDto;
import com.example.polls.payload.role.RoleBaseDto;

import java.util.Set;

public class UserBasicDto extends UserBaseDto {

    private Set<RoleBaseDto> roles;

    private Set<PermissionDto> permissions;

    public Set<RoleBaseDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBaseDto> roles) {
        this.roles = roles;
    }

    public Set<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDto> permissions) {
        this.permissions = permissions;
    }
}

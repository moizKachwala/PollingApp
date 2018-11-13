package com.example.polls.payload.role;

import java.util.HashSet;
import java.util.Set;

public class RoleBasicDto extends RoleBaseDto {

    private Set<PermissionDto> permissions = new HashSet<>();

    public Set<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDto> permissions) {
        this.permissions = permissions;
    }
}

package com.example.polls.mapper;

import com.example.polls.model.Permission;
import com.example.polls.model.Role;
import com.example.polls.model.User;
import com.example.polls.payload.user.UserBasicDto;
import java.util.HashSet;
import java.util.Set;

public abstract class UserMapperDecorator implements UserMapper {

    private UserMapper userMapper;

    public UserMapperDecorator(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserBasicDto UserToUserBasicDto(User user) {
        UserBasicDto userDto = userMapper.UserToUserBasicDto(user);

        Set<Permission> permissions = new HashSet<>();

        if(!user.getRoles().isEmpty()) {
            for(Role role: user.getRoles()) {
                permissions.addAll(role.getPermissions());
            }
        }

        userDto.setPermissions(userMapper.permissionSetToPermissionDtoSet(permissions));

        return userDto;
    }
}

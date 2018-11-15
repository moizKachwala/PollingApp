package com.example.polls.mapper;

import com.example.polls.model.Role;
import com.example.polls.payload.role.RoleBasicDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    RoleBasicDto roleToRoleBasicDto(Role role);
}

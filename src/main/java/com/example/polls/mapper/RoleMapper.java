package com.example.polls.mapper;

import com.example.polls.model.Role;
import com.example.polls.payload.role.RoleBasicDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    RoleBasicDto roleToRoleBasicDto(Role role);

    Role roleBasicDtoToRole(RoleBasicDto roleBasicDto);

    List<RoleBasicDto> roleListToRoleBasicDtoList(List<Role> roles);
}

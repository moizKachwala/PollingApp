package com.example.polls.mapper;

import com.example.polls.model.Permission;
import com.example.polls.payload.role.PermissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface PermissionMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    Set<PermissionDto> permissionSetToPermissionDtoSet(Set<Permission> permissions);

    Set<Permission> permissionDtoSetToPermissionSet(Set<PermissionDto> permissionDto);
}

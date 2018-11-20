package com.example.polls.service;

import com.example.polls.payload.role.RoleBasicDto;

import java.util.List;

public interface RoleService {

    RoleBasicDto getById(Long id);

    List<RoleBasicDto> list();

    RoleBasicDto create(RoleBasicDto roleBasicDto);

    RoleBasicDto update(Long id, RoleBasicDto roleBasicDto);

    void delete(Long id);
}

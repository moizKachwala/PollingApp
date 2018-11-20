package com.example.polls.service.impl;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.mapper.PermissionMapper;
import com.example.polls.mapper.RoleMapper;
import com.example.polls.model.Permission;
import com.example.polls.model.Role;
import com.example.polls.payload.role.RoleBasicDto;
import com.example.polls.repository.RoleRepository;
import com.example.polls.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleBasicDto getById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        RoleBasicDto roleBasicDto = RoleMapper.MAPPER.roleToRoleBasicDto(role);

        return roleBasicDto;
    }

    @Override
    public List<RoleBasicDto> list() {

        List<Role> roles = roleRepository.findAll();
        return RoleMapper.MAPPER.roleListToRoleBasicDtoList(roles);
    }

    @Override
    public RoleBasicDto create(RoleBasicDto roleBasicDto) {
        if(roleRepository.existsByName(roleBasicDto.getName())) {
            throw new IllegalArgumentException("role name already exist");
        }

        Role role = RoleMapper.MAPPER.roleBasicDtoToRole(roleBasicDto);

        Set<Permission> permissions = role.getPermissions();

        if(permissions.size() < 0) {
            throw new IllegalArgumentException("At least one permission should be added!");
        }

        Role newRole = roleRepository.save(role);

        return RoleMapper.MAPPER.roleToRoleBasicDto(newRole);
    }

    @Override
    public RoleBasicDto update(Long id, RoleBasicDto roleBasicDto) {

        Optional<Role> role = roleRepository.findById(roleBasicDto.getId());
        if(role.isPresent()) {
            Role r = role.get();
            r.setDescription(roleBasicDto.getDescription());
            Set<Permission> permissions = PermissionMapper.MAPPER.permissionDtoSetToPermissionSet(roleBasicDto.getPermissions());
            r.setPermissions(permissions);

            Role updatedRole = roleRepository.save(r);

            return RoleMapper.MAPPER.roleToRoleBasicDto(updatedRole);
        }
        else {
            throw new IllegalArgumentException("role was not found");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Role> role = roleRepository.findById(id);

        if(role.isPresent()) {
            roleRepository.delete(role.get());
        }
        else {
            throw new IllegalArgumentException("role was not found");
        }
    }
}

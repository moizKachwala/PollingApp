package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.mapper.PermissionMapper;
import com.example.polls.mapper.RoleMapper;
import com.example.polls.model.Permission;
import com.example.polls.model.Role;
import com.example.polls.model.User;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.role.PermissionDto;
import com.example.polls.payload.role.RoleBaseDto;
import com.example.polls.payload.role.RoleBasicDto;
import com.example.polls.payload.user.UserDto;
import com.example.polls.repository.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LogManager.getLogger(RoleController.class);

    @GetMapping("/roles/{id}")
    public RoleBasicDto getUserById(@PathVariable(value="id") Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        RoleBasicDto roleBasicDto = RoleMapper.MAPPER.roleToRoleBasicDto(role);
        return roleBasicDto;
    }

    @PostMapping("/roles")
    public ResponseEntity<?> post(@RequestBody RoleBasicDto roleBasicDto) {
        if(roleRepository.existsByName(roleBasicDto.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Role name already exist!"),
                    HttpStatus.BAD_REQUEST);
        }

        Role role = RoleMapper.MAPPER.roleBasicDtoToRole(roleBasicDto);

        Set<Permission> permissions = role.getPermissions();

        if(permissions.size() < 0) {
            return new ResponseEntity(new ApiResponse(false, "At least one permission needs to be added!"),
                    HttpStatus.BAD_REQUEST);
        }

        Role result = roleRepository.save(role);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/roles/{roleName}")
                .buildAndExpand(result.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Role created successfully"));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> put(@PathVariable(value="id") Long id, @RequestBody RoleBasicDto roleBasicDto) {

        Optional<Role> role = roleRepository.findById(roleBasicDto.getId());
        if(role.isPresent()) {
            Role r = role.get();
            r.setDescription(roleBasicDto.getDescription());
            Set<Permission> permissions = PermissionMapper.MAPPER.permissionDtoSetToPermissionSet(roleBasicDto.getPermissions());
            r.setPermissions(permissions);

            roleRepository.save(r);
            return ResponseEntity.ok(new ApiResponse(true, "Role updated successfully"));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {

        Optional<Role> role = roleRepository.findById(id);

        if(role.isPresent()) {
            roleRepository.delete(role.get());

            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}

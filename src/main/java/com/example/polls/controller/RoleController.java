package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.mapper.RoleMapper;
import com.example.polls.model.Role;
import com.example.polls.payload.role.RoleBasicDto;
import com.example.polls.repository.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

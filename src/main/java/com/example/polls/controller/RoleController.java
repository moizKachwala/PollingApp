package com.example.polls.controller;

import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.role.RoleBasicDto;
import com.example.polls.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    private static final Logger logger = LogManager.getLogger(RoleController.class);

    @GetMapping("/roles/{id}")
    public RoleBasicDto getUserById(@PathVariable(value="id") Long id) {
        RoleBasicDto role = roleService.getById(id);
        return role;
    }

    @PostMapping("/roles")
    public ResponseEntity<?> post(@RequestBody RoleBasicDto roleBasicDto) {
        RoleBasicDto newRole = roleService.create(roleBasicDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/roles/{roleName}")
                .buildAndExpand(newRole.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Role created successfully"));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> put(@PathVariable(value="id") Long id, @RequestBody RoleBasicDto roleBasicDto) {

        roleService.update(id, roleBasicDto);
        return ResponseEntity.ok(new ApiResponse(true, "Role updated successfully"));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {

        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}

package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.mapper.UserMapper;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.payload.user.UserBaseDto;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.payload.user.UserDto;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.security.CurrentUser;
import com.example.polls.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserBasicDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserBasicDto userBasicDto = userService.getById(currentUser.getId());
        return userBasicDto;
    }

    @GetMapping("/users/list")
    public List<UserBaseDto> list() {

        List<UserBaseDto> users = userService.list();
        return users;
    }

    @GetMapping("/users")
    public UserBasicDto getUserProfile(@RequestParam(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserBasicDto userBasicDto = UserMapper.MAPPER.UserToUserBasicDto(user);
        return userBasicDto;
    }

    @GetMapping("/users/{id}")
    public UserBasicDto getUserById(@PathVariable(value="id") Long id) {
        UserBasicDto userBasicDto = userService.getById(id);
        return userBasicDto;
    }

    @PostMapping("/users")
    public ResponseEntity<?> post(@Valid @RequestBody UserDto userDto) {

        UserBasicDto result = userService.create(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User created successfully"));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> put(@PathVariable(value="id") Long id, @Valid @RequestBody UserDto userDto) {
        UserBasicDto result = userService.update(userDto, id);
        return ResponseEntity.ok(new ApiResponse(true, "User updated successfully"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {

        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}

package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.mapper.UserMapper;
import com.example.polls.model.Role;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.payload.role.RoleBaseDto;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.payload.user.UserDto;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.security.CurrentUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserBasicDto userBasicDto = UserMapper.MAPPER.UserToUserBasicDto(user);
        return userBasicDto;
    }

    @PostMapping("/users")
    public ResponseEntity<?> post(@Valid @RequestBody UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        for (RoleBaseDto role: userDto.getRoles()) {
            Optional<Role> existingRole = roleRepository.findById(role.getId());

            if(!existingRole.isPresent()) {
                return new ResponseEntity(new ApiResponse(false, "Role was not found!"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        User user = UserMapper.MAPPER.UserDtoToUser(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User created successfully"));
    }

    @PutMapping("/users")
    public ResponseEntity<?> put(@Valid @RequestBody UserDto userDto) {

        for (RoleBaseDto role: userDto.getRoles()) {
            Optional<Role> existingRole = roleRepository.findById(role.getId());

            if(!existingRole.isPresent()) {
                return new ResponseEntity(new ApiResponse(false, "Role was not found!"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        Optional<User> user = userRepository.findById(userDto.getId());
        if(user.isPresent()) {

            User u = user.get();

            if(null != userDto.getEmail() && !u.getEmail().equalsIgnoreCase(userDto.getEmail())) {
                if(userRepository.existsByEmail(userDto.getEmail())) {
                    return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                            HttpStatus.BAD_REQUEST);
                }
            }

            u.setFirstname(userDto.getFirstname());
            u.setLastname(userDto.getLastname());
            u.setEmail(userDto.getEmail());

            userRepository.save(u);
            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully"));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            userRepository.delete(user.get());

            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}

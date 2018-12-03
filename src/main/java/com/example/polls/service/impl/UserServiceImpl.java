package com.example.polls.service.impl;

import com.example.polls.exception.RecordNotFoundException;
import com.example.polls.mapper.UserMapper;
import com.example.polls.model.Role;
import com.example.polls.model.User;
import com.example.polls.payload.role.RoleBaseDto;
import com.example.polls.payload.user.UserBaseDto;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.payload.user.UserDto;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserBasicDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("user.not.found"));

        return UserMapper.MAPPER.UserToUserBasicDto(user);
    }

    @Override
    public List<UserBaseDto> list() {
        List<User> users = userRepository.findAll();

        List<UserBaseDto> usersListDto = UserMapper.MAPPER.UserListToUserBaseDtoList(users);

        return usersListDto;
    }

    @Override
    public UserBasicDto create(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new IllegalArgumentException("Username already exist");
        }


        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exist");
        }

        for (RoleBaseDto role: userDto.getRoles()) {
            Optional<Role> existingRole = roleRepository.findById(role.getId());

            if(!existingRole.isPresent()) {
                throw  new IllegalArgumentException("Role was not found");
            }
        }

        User user = UserMapper.MAPPER.UserDtoToUser(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        UserBasicDto userBasicDto = UserMapper.MAPPER.UserToUserBasicDto(result);
        return userBasicDto;
    }

    @Override
    public UserBasicDto update(UserDto userDto, Long id) {

        for (RoleBaseDto role: userDto.getRoles()) {
            Optional<Role> existingRole = roleRepository.findById(role.getId());

            if(!existingRole.isPresent()) {
                throw  new IllegalArgumentException("Role was not found");
            }
        }

        Optional<User> user = userRepository.findById(userDto.getId());
        if(user.isPresent()) {

            User u = user.get();

            if(null != userDto.getEmail() && !u.getEmail().equalsIgnoreCase(userDto.getEmail())) {
                if (userRepository.existsByEmail(userDto.getEmail())) {
                    throw new IllegalArgumentException("Email already exist");
                }
            }

            u.setFirstname(userDto.getFirstname());
            u.setLastname(userDto.getLastname());
            u.setEmail(userDto.getEmail());

            User result = userRepository.save(u);

            UserBasicDto userBasicDto = UserMapper.MAPPER.UserToUserBasicDto(result);
            return userBasicDto;
        }
        else {
            throw new IllegalArgumentException("User was not found");
        }
    }

    @Override
    public void delete(Long id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            userRepository.delete(user.get());
        }
        else {
            throw new IllegalArgumentException("User was not found");
        }
    }
}

package com.example.polls.service;

import com.example.polls.payload.user.UserBaseDto;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.payload.user.UserDto;

import java.util.List;

public interface UserService {

    UserBasicDto getById(Long id);

    List<UserBaseDto> list();

    UserBasicDto create(UserDto userDto);

    UserBasicDto update(UserDto userDto, Long id);

    void delete(Long id);
}

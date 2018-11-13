package com.example.polls.mapper;

import com.example.polls.model.User;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.payload.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserBasicDto UserToUserBasicDto(User user);

    User UserDtoToUser(UserDto userDto);
}

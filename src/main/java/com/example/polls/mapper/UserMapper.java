package com.example.polls.mapper;

import com.example.polls.model.User;
import com.example.polls.payload.user.UserBaseDto;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.payload.user.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper extends PermissionMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping( target = "permissions", ignore = true )
    UserBasicDto UserToUserBasicDto(User user);

    User UserDtoToUser(UserDto userDto);

    List<UserBaseDto> UserListToUserBaseDtoList(List<User> users);
}

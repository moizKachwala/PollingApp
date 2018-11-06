package com.example.polls.mapper;

import com.example.polls.model.User;
import com.example.polls.payload.UserBasic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserBasic UserToUserBasic(User user);
}

package com.bms.backend.mapper;


import com.bms.backend.dto.UserDto;
import com.bms.backend.entity.User;

public interface UserMapper {

    UserDto toUserDto(User user);
}
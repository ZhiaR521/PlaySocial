package com.zhiar.mapper;

import com.zhiar.dto.UserDto;
import com.zhiar.entity.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}

package com.zdjavapol110.eventmanager.core.modules.user.mapper;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.service.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto map(UserEntity user) {
        return new UserDto()
                .setId(user.getId())
                .setUuid(user.getUuid())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setPassword(user.getPassword())
                .setEmail(user.getEmail())
                .setRole(user.getRole());

    }

    public static List<UserDto> map(List<UserEntity> users) {
        return users
                .stream()
                .map(UserMapper::map)
                .collect(Collectors.toList());
    }
}

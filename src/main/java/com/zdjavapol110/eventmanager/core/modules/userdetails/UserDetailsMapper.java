package com.zdjavapol110.eventmanager.core.modules.userdetails;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

  public UserEntity mapToUserEntity(UserReadDto createdBy) {
    if (createdBy == null || createdBy.getId() == null) {
      return null;
    }
    return UserEntity.builder().id(createdBy.getId()).build();
  }

  public UserReadDto mapToUserDto(UserEntity user) {
    if (user == null) {
      return null;
    }
    return UserReadDto.builder().id(user.getId()).displayName(user.getEmail()).build();
  }
}

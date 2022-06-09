package com.zdjavapol110.eventmanager.core.modules.userdetails;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsMapper {

  private final UserRepository userRepository;

  public UserEntity mapToUserEntity(UserReadDto createdBy) {
    if (createdBy == null || createdBy.getId() == null) {
      return null;
    }
    return userRepository.getReferenceById(createdBy.getId());
  }

  public UserReadDto mapToUserDto(UserEntity user) {
    if (user == null) {
      return null;
    }
    return UserReadDto.builder().id(user.getId()).displayName(user.getEmail()).build();
  }
}

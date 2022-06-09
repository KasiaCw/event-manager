package com.zdjavapol110.eventmanager.core.modules.userdetails;

import com.zdjavapol110.eventmanager.core.modules.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Service
class UserDetailsServiceImpl implements UserDetailsService {
  @Override
  public Optional<UserReadDto> getUserDetailsFromRequest(HttpServletRequest request) {
    Principal userPrincipal = request.getUserPrincipal();
    if (userPrincipal instanceof Authentication) {
      Authentication authentication = (Authentication) userPrincipal;
      Object genericPrincipal = authentication.getPrincipal();
      if (genericPrincipal instanceof CustomUserDetails) {
        CustomUserDetails customUserDetails = (CustomUserDetails) genericPrincipal;
        return Optional.of(
            UserReadDto.builder()
                .id(customUserDetails.getId())
                .displayName(customUserDetails.getUsername())
                .build());
      }
    }
    return Optional.empty();
  }

  @Override
  public UserReadDto getRequiredUserDetailsFromRequest(HttpServletRequest request) {
    return getUserDetailsFromRequest(request)
        .filter(userReadDto -> userReadDto.getId() != null)
        .orElseThrow(UnauthorizedUserException::new);
  }
}

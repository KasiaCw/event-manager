package com.zdjavapol110.eventmanager.core.modules.user.webconfig;

import com.zdjavapol110.eventmanager.core.modules.user.UserRole;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configurable
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(authentication.getName()));
        if (user.isPresent()) {
            if (passwordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword())) {
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorityList);
            } else {
                throw new BadCredentialsException("Wrong Password");
            }
        } else {
            throw new BadCredentialsException("Wrong UserName");
        }
    }

}

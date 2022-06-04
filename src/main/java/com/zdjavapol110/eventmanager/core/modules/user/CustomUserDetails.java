package com.zdjavapol110.eventmanager.core.modules.user;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;
    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorityList;

    public CustomUserDetails(UserEntity userEntity) {
        this.userName = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.active = userEntity.isActive();
        this.authorityList = Arrays.stream(userEntity.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return userEntity.getFirstName() + " " + userEntity.getLastName();
    }
}

package com.zdjavapol110.eventmanager.core.modules.user.service;

import com.zdjavapol110.eventmanager.core.modules.user.repository.RoleRepository;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerDefaultUser(UserEntity user) {
        Role roleUser = roleRepo.findByName("user");
        user.addRole(roleUser);

        userRepo.save(user);
    }
}

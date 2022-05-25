package com.zdjavapol110.eventmanager.core.modules.user.SignUp;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import com.zdjavapol110.eventmanager.core.modules.user.service.UserService;
import com.zdjavapol110.eventmanager.core.modules.user.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String getSignUp(Model model) {
        model.addAttribute("signup", new UserDto());
        model.addAttribute("user", new UserDto());
        return "signup/signup.html";
    }

    @PostMapping("/process_register")
    public String processRegister(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "register_success";
    }


}



package com.zdjavapol110.eventmanager.core.modules.user.registration;

import com.zdjavapol110.eventmanager.core.modules.event.EventService;
import com.zdjavapol110.eventmanager.core.modules.user.repository.ERole;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import com.zdjavapol110.eventmanager.core.modules.user.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

import static com.zdjavapol110.eventmanager.core.modules.user.webconfig.LoginUtil.isUserLogged;

@Controller
public class SignUpController {
  @Autowired private UserRepository userRepository;

  @Autowired private EventService eventService;

  @Autowired BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/signup")
  public String getSignUp(Model model) {
    if(isUserLogged()){
      return "redirect:/";
    }
      model.addAttribute("signup", new UserDto());
      model.addAttribute("user", new UserDto());
      return "signup/signup.html";
  }

  @PostMapping("/signup")
  public String processRegister(@Valid UserEntity user, Model model) {
    if(isUserLogged()){
      return "redirect:/";
    }
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setRole(ERole.USER);

    try {
      userRepository.save(user);
      model.addAttribute("errorMsg", null);
    } catch (Exception ex) {
      model.addAttribute("errorMsg", "User already exists.");
      model.addAttribute("signup", new UserDto());
      model.addAttribute("user", new UserDto());
      return "signup/signup.html";
    }
    model.addAttribute("user", new UserDto());
    return "signup/register_success";
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    if(isUserLogged()){
      return "redirect:/";
    }
    model.addAttribute("user", new UserDto());
    return "signup/login2.html";
  }

  @PostMapping("/process_success")
  public String processEvents(Model model) {
    model.addAttribute("events", eventService.getAllEvents(0, 10));
    return "redirect:/events";
  }

  @GetMapping("/users")
  public String listUsers(Model model) {
      List<UserEntity> listUsers = userRepository.findAll();
      model.addAttribute("listUsers", listUsers);
      return "users/users.html";
  }
}

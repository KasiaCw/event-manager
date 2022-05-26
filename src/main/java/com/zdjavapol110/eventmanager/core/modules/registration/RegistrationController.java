package com.zdjavapol110.eventmanager.core.modules.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/register")
    public String register(Model model) {
        return "/registration/register-form.html";
    }

}

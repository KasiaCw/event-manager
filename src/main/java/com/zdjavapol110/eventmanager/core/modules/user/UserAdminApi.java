package com.zdjavapol110.eventmanager.core.modules.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.management.relation.Role;

@Api(tags = "UserAdmin")
@RestController
@RequestMapping(path = "api/admin/user")
@RolesAllowed(Role.USER_ADMIN)
public class UserAdminApi {

    @GetMapping("/admin")
    public String admin(){
        return "Welcome admin to TipsToCode Site!";
    }
}

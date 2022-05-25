package com.zdjavapol110.eventmanager.core.modules.user.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class UserForm {

    private String uuid;

    private String email;

    private String first_name;

    private String last_name;

    private Set<String> tags = Set.of();


}

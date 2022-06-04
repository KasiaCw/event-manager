package com.zdjavapol110.eventmanager.core.modules.user.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@Getter
@Setter
@ToString
@Accessors(fluent = false, chain = true)
public class UserDto {

    private long id;

    private String uuid;

    private String email;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String role;
}

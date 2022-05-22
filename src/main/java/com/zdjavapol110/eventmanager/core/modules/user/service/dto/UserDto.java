package com.zdjavapol110.eventmanager.core.modules.user.service.dto;

import com.zdjavapol110.eventmanager.core.modules.user.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "book")
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

    private UserRole role;
}

package com.zdjavapol110.eventmanager.core.modules.entity;

import com.zdjavapol110.eventmanager.core.modules.user.UserRole;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String uuid;

    private String email;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private UserRole role;

}

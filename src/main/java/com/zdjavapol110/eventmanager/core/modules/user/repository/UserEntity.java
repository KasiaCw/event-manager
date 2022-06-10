package com.zdjavapol110.eventmanager.core.modules.user.repository;

import com.zdjavapol110.eventmanager.core.modules.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = {"email"})
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private ERole role;

  @Column(name = "active", columnDefinition = "tinyint default 1")
  private boolean active = true;

//  @ManyToMany
////          @JoinTable(
////                  name ="users_event",
////                  joinColumns = {
////                          @JoinColumn(name = "uuid")
////                  },
////                  inverseJoinColumns = {
////                          @JoinColumn(name = "id")
////                  }
////          )
//  Set<Event> events = new HashSet<>();

}

package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "event")
@Data
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private EventState status = EventState.NOT_PUBLISHED;

  @ManyToOne()
  private UserEntity user;

}

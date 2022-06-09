package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.event.comments.Comment;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "event") // , cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private EventState status = EventState.NOT_PUBLISHED;

  @ManyToOne(fetch = FetchType.EAGER)
  private UserEntity createdBy;
}

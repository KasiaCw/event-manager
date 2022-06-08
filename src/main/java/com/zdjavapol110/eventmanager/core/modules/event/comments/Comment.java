package com.zdjavapol110.eventmanager.core.modules.event.comments;

import com.zdjavapol110.eventmanager.core.modules.event.Event;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity createdBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Column(name = "created_date")
  private LocalDate createdDate;
}

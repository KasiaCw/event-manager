package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

  private Long id;
  // private String uuid;
  private String title;
  private LocalDate startDate;
  private LocalDate endDate;
  private String description;
}

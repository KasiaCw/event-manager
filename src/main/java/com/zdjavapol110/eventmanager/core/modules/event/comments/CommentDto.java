package com.zdjavapol110.eventmanager.core.modules.event.comments;

import com.zdjavapol110.eventmanager.core.modules.userdetails.UserReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
  private Long id;
  private String body;
  private UserReadDto createdBy;
}

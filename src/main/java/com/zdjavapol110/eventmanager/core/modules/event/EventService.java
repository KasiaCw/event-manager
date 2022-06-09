package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.userdetails.UserReadDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

  EventDto createEvent(EventDto eventDto);

  Page<EventDto> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir);

  EventDto getEventById(long id);

  EventDto updateEvent(EventDto eventDto);

  List<EventDto> getAllEvents(int pageNo, int pageSize);

  Page<EventDto> findByTitle(
      int pageNo, int pageSize, String sortBy, String sortDir, String keyword);

  void deleteEvent(Long id, UserReadDto deletedBy);
}

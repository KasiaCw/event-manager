package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EventService {

  EventDto createEvent(EventDto eventDto);

  Page<EventDto> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir);

  EventDto getEventById(long id);

  EventDto updateEvent(EventDto eventDto);

  List<EventDto> getAllEvents(int pageNo, int pageSize);

  Page<EventDto> findByTitle(int pageNo, int pageSize, String sortBy, String sortDir, String keyword);

  void deleteEvent(Long id);

  void participateUserEntityToEvent(Long eventId, Set<UserEntity> userEntity);

}


package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.event.EventDto;

import java.util.List;

public interface EventService {

    EventDto createEvent(EventDto eventDto);

    List<EventDto>getAllEvents(int pageNo, int pageSize);

    EventDto getEventById(long id);

    EventDto updateEvent(EventDto eventDto, long id);

    void deleteEventById (long id);
}

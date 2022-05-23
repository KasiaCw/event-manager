package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Eventserviceimpl implements EventService {

    private final EventRepository eventRepository;


    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = mapToEntity(eventDto);
        Event newEvent = eventRepository.save(event);
        return mapToDTO(newEvent);
    }

    @Override
    public List<EventDto> getAllEvents(int pageNo, int pageSize) {

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Event> events = eventRepository.findAll(pageable);


        //get content from page object
        List<Event> listOfEvents = events.getContent();
        return listOfEvents.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(long id) {
        Event event = eventRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Event", "id", id));
        return mapToDTO(event);
    }

    @Override
    public EventDto updateEvent(EventDto eventDto, long id) {
        //get event by id from database
        Event event = eventRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Event", "id", id));
        event.setTitle(eventDto.getTitle());
        event.setDate(eventDto.getDate());
        event.setDescription(eventDto.getDescription());

        Event updateEvent = eventRepository.save(event);
        return mapToDTO(updateEvent);
    }

    @Override
    public void deleteEventById(long id) {
        Event event = eventRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Event", "id", id));
        eventRepository.delete(event);

    }

    //convert DTO to Entity
    private Event mapToEntity(EventDto eventDto){
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setDate(eventDto.getDate());
        event.setDescription(eventDto.getDescription());
        return event;
    }

    //convert entity to DTO
    private EventDto mapToDTO( Event event){
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDescription(event.getDescription());
        eventDto.setDate(event.getDate());
        return eventDto;
    }
}

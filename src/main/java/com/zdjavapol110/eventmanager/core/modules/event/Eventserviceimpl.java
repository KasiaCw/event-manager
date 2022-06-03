package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public List<EventDto> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir) {

    Sort sort =
        Sort.Direction.DESC.name().equalsIgnoreCase(sortDir)
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    // create Pageable instance
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Page<Event> events = eventRepository.findAll(pageable);

    // get content from page object
    List<Event> listOfEvents = events.getContent();
    return listOfEvents.stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  @Override
  public EventDto getEventById(long id) {
    Event event =
        eventRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    return mapToDTO(event);
  }

  @Override
  public EventDto updateEvent(EventDto eventDto) {
    Event event =
        eventRepository
            .findById(eventDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventDto.getId()));
    event.setTitle(eventDto.getTitle());
    event.setStartDate(eventDto.getStartDate());
    event.setEndDate(eventDto.getEndDate());
    event.setDescription(eventDto.getDescription());

    Event updateEvent = eventRepository.save(event);
    return mapToDTO(updateEvent);
  }

  @Override
  public List<EventDto> getAllEvents(int pageNo, int pageSize) {
    return getAllEvents(pageNo, pageSize, "startDate", "asc");
  }

  @Override
  public List<EventDto> findByTitle(int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {
    Sort sort =
            Sort.Direction.DESC.name().equalsIgnoreCase(sortDir)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

    // create Pageable instance
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    List<Event> eventsList = eventRepository.findByTitle(keyword);

    // get content from page object
   // List<Event> listOfEvents = events.getContent();
    return eventsList.stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  @Override
  public void deleteEvent(Long id) {
//    Event event = eventRepository.getReferenceById(id);
    eventRepository.deleteById(id);
//    eventRepository.delete(event);
  }


  private Event mapToEntity(EventDto eventDto) {
    Event event = new Event();
    event.setId(eventDto.getId());
    event.setTitle(eventDto.getTitle());
    event.setStartDate(eventDto.getStartDate());
    event.setEndDate(eventDto.getEndDate());
    event.setDescription(eventDto.getDescription());
    return event;
  }

  private EventDto mapToDTO(Event event) {
    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    eventDto.setTitle(event.getTitle());
    eventDto.setDescription(event.getDescription());
    eventDto.setStartDate(event.getStartDate());
    eventDto.setEndDate(event.getEndDate());
    return eventDto;
  }
}

package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.userdetails.UserDetailsMapper;
import com.zdjavapol110.eventmanager.core.modules.userdetails.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
class Eventserviceimpl implements EventService {

  private final EventRepository eventRepository;
  private final UserDetailsMapper userDetailsMapper;

  @Override
  @Transactional
  public EventDto createEvent(EventDto eventDto) {
    Event event = mapToEntity(eventDto);
    Event newEvent = eventRepository.save(event);
    return mapToDTO(newEvent);
  }

  @Override
  @Transactional
  public Page<EventDto> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir) {

    Sort sort =
        Sort.Direction.DESC.name().equalsIgnoreCase(sortDir)
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Page<Event> events = eventRepository.findAll(eventRepository.onlyPublished(), pageable);

    return events.map(this::mapToDTO);
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
    return getAllEvents(pageNo, pageSize, "startDate", "asc").getContent();
  }

  @Override
  public Page<EventDto> findByTitle(
      int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {
    Sort sort =
        Sort.Direction.DESC.name().equalsIgnoreCase(sortDir)
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Specification<Event> conditionSpec =
        eventRepository.onlyPublished().and(eventRepository.onlyTitle(keyword));
    Page<Event> events = eventRepository.findAll((conditionSpec), pageable);

    return events.map(this::mapToDTO);
  }

  @Override
  public void deleteEvent(Long id, UserReadDto deleteRequestedBy) {
    eventRepository
        .findById(id)
        .map(Event::getCreatedBy)
        .filter(Objects::nonNull)
        .map(UserEntity::getId)
        .filter(deleteRequestedBy.getId()::equals)
        .orElseThrow(ModificationForbiddenException::new);

    eventRepository.deleteById(id);
  }

  private Event mapToEntity(EventDto eventDto) {
    Event event = new Event();
    event.setId(eventDto.getId());
    event.setTitle(eventDto.getTitle());
    event.setStartDate(eventDto.getStartDate());
    event.setEndDate(eventDto.getEndDate());
    event.setDescription(eventDto.getDescription());
    event.setStatus(eventDto.getStatus());
    event.setCreatedBy(userDetailsMapper.mapToUserEntity(eventDto.getCreatedBy()));
    return event;
  }

  private EventDto mapToDTO(Event event) {
    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    eventDto.setTitle(event.getTitle());
    eventDto.setDescription(event.getDescription());
    eventDto.setStartDate(event.getStartDate());
    eventDto.setEndDate(event.getEndDate());
    eventDto.setStatus(event.getStatus());
    eventDto.setCreatedBy(userDetailsMapper.mapToUserDto(event.getCreatedBy()));
    return eventDto;
  }
}

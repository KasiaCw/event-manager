package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import com.zdjavapol110.eventmanager.core.modules.user.service.StudentCourseIllegalStateException;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class Eventserviceimpl implements EventService {

  private final EventRepository eventRepository;
  private final UserDetailsMapper userDetailsMapper;
  private final UserRepository userRepository;

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
    Specification<Event> conditionSpec = eventRepository.onlyPublished();
    return getEventsBySpecification(pageNo, pageSize, sortBy, sortDir, conditionSpec);
  }

  @Override
  public Page<EventDto> getAllEventsAfter(
      LocalDate date, int pageNo, int pageSize, String sortBy, String sortDir) {
    Specification<Event> conditionSpec =
        eventRepository.onlyPublished().and(eventRepository.endsAfter(date));
    return getEventsBySpecification(pageNo, pageSize, sortBy, sortDir, conditionSpec);
  }

  @Override
  @Transactional
  public Page<EventDto> findByTitle(
      int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {
    Specification<Event> conditionSpec =
        eventRepository.onlyPublished().and(eventRepository.onlyTitle(keyword));
    return getEventsBySpecification(pageNo, pageSize, sortBy, sortDir, conditionSpec);
  }

  private Page<EventDto> getEventsBySpecification(
      int pageNo, int pageSize, String sortBy, String sortDir, Specification<Event> conditionSpec) {
    Sort sort =
        Sort.Direction.DESC.name().equalsIgnoreCase(sortDir)
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Page<Event> events = eventRepository.findAll(conditionSpec, pageable);
    return events.map(this::mapToDTO);
  }

  @Override
  @Transactional
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
  @Transactional
  public void deleteEvent(Long id, UserReadDto deleteRequestedBy) {
    EventDto eventDto = getEventById(id);
    if (!canDelete(eventDto, Optional.ofNullable(deleteRequestedBy))) {
      throw new ModificationForbiddenException();
    }
    eventRepository.deleteById(id);
  }

  @Override
  public EventDto setCanDelete(EventDto eventDto, Optional<UserReadDto> currentUser) {
    return eventDto.toBuilder().canDelete(canDelete(eventDto, currentUser)).build();
  }

  private boolean canDelete(EventDto eventDto, Optional<UserReadDto> currentUser) {
    boolean canDelete =
        currentUser
            .map(UserReadDto::getId)
            .filter(
                userId ->
                    userId != null
                        && eventDto.getCreatedBy() != null
                        && userId.equals(eventDto.getCreatedBy().getId()))
            .isPresent();
    return canDelete;
  }

  @Override
  @Transactional
  public void addParticipantToEvent(Long eventId, Long userId) {
    eventRepository
        .findById(eventId)
        .map(event -> event.addParticipant(userRepository.getReferenceById(userId)))
        .ifPresent(eventRepository::save);
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
    eventDto.setNumberOfParticipants((event.getParticipants().size()));
    return eventDto;
  }

  @Override
  public Optional<Event> getEventByEventName(String eventName) {
    return eventRepository.findUsersByEventName(eventName);
  }

  @Override
  public void registerUserToEvent(Long eventId, Set<UserEntity> userEntity) {
    Optional<Event> eventOptional = eventRepository.findById(eventId);
    if (eventOptional.isEmpty()) {
      throw new StudentCourseIllegalStateException(
          "Failed to register User. Invalid EventId :: " + eventId);
    }
    Event event = eventOptional.get();
    //    userEntity.addAll(event.getUsers());
    userEntity.addAll((Collection<? extends UserEntity>) event.getCreatedBy());
    //    event.setUsers(userEntity);
    eventRepository.save(event);
  }
}

package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.event.comments.CommentDto;
import com.zdjavapol110.eventmanager.core.modules.event.comments.CommentRepository;
import com.zdjavapol110.eventmanager.core.modules.event.comments.CommentService;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserRepository;
import com.zdjavapol110.eventmanager.core.modules.userdetails.UserDetailsMapper;
import com.zdjavapol110.eventmanager.core.modules.userdetails.UserReadDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {

  @Autowired EventService eventService;
  @Autowired EventRepository eventRepository;

  @Autowired CommentService commentService;
  @Autowired CommentRepository commentRepository;
  @Autowired UserRepository userRepository;
  @Autowired UserDetailsMapper userDetailsMapper;
  private UserReadDto userReadDto;

  @AfterEach
  void cleanup() {
    commentRepository.deleteAll();
    eventRepository.deleteAll();
    userRepository.deleteAll();
  }

  @BeforeEach
  @Transactional
  void initTestData() {
    UserEntity userEntity =
        userRepository.save(
            UserEntity.builder().username("testuser").email("testuser@gmail.com").build());
    this.userReadDto = userDetailsMapper.mapToUserDto(userEntity);
  }

  @Test
  void shouldFindAll() {
    // given
    EventDto firstEvent =
        eventService.createEvent(
            eventFixture()
                .title("Event in January")
                .description("description")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 1, 2))
                .build());
    EventDto secoundEvent =
        eventService.createEvent(
            eventFixture()
                .title("Event in February")
                .description("description")
                .startDate(LocalDate.of(2021, 9, 1))
                .endDate(LocalDate.of(2021, 2, 2))
                .build());
    EventDto marchEvent =
        eventService.createEvent(
            eventFixture()
                .title("Event in March")
                .description("description")
                .startDate(LocalDate.of(2021, 3, 1))
                .endDate(LocalDate.of(2021, 3, 2))
                .build());

    // when

    int pageNo = 0;
    int pageSize = 10;
    String sortBy = "startDate";
    String sortDir = "ASC";
    Page<EventDto> allEvents = eventService.getAllEvents(pageNo, pageSize, sortBy, sortDir);

    // then

    assertThat(allEvents).containsExactly(firstEvent, marchEvent, secoundEvent);
  }

  @Test
  void shouldCreateAndFindById() {
    // given
    EventDto event = eventFixture().build();

    // when
    EventDto createdEvent = eventService.createEvent(event);

    // then
    assertThat(createdEvent).usingRecursiveComparison().ignoringFields("id").isEqualTo(event);
  }

  @Test
  void shouldUpdateEvent() {
    // given
    EventDto initialEvent = eventService.createEvent(eventFixture().build());
    EventDto eventUpdate =
        eventFixture()
            .id(initialEvent.getId())
            .title("Updated title")
            .description("Updated description")
            .startDate(LocalDate.of(2021, 5, 1))
            .endDate(LocalDate.of(2021, 5, 2))
            .build();

    // when
    eventService.updateEvent(eventUpdate);
    EventDto eventAfterUpdate = eventService.getEventById(initialEvent.getId());

    // then
    assertThat(eventAfterUpdate).isEqualTo(eventUpdate).isNotEqualTo(initialEvent);
  }

  @Test
  void shouldShearchByTitle() {
    // given
    EventDto matchingEvent =
        eventService.createEvent(eventFixture().title("matching aaa title").build());
    EventDto notMatchingEvent =
        eventService.createEvent(eventFixture().title("not matching bbb title").build());
    String keyword = "aaa";

    // when

    int pageNo = 0;
    int pageSize = 10;
    String sortBy = "startDate";
    String sortDir = "ASC";
    Page<EventDto> matchingEvents =
        eventService.findByTitle(pageNo, pageSize, sortBy, sortDir, keyword);

    // then

    assertThat(matchingEvents).containsExactly(matchingEvent).doesNotContain(notMatchingEvent);
  }

  @Test
  void shouldDeleteEvent() {
    // given
    EventDto eventToDelete =
        eventService.createEvent(
            eventFixture()
                .title("Event to delete")
                .description("description")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 1, 2))
                .build());
    EventDto eventToStay =
        eventService.createEvent(
            eventFixture()
                .title("Event to stay")
                .description("description")
                .startDate(LocalDate.of(2021, 9, 1))
                .endDate(LocalDate.of(2021, 2, 2))
                .build());
    // when
    UserReadDto deletedBy = userReadFixture().build();
    eventService.deleteEvent(eventToDelete.getId(), deletedBy);
    int pageNo = 0;
    int pageSize = 10;
    String sortBy = "startDate";
    String sortDir = "ASC";
    Page<EventDto> allEvents = eventService.getAllEvents(pageNo, pageSize, sortBy, sortDir);
    // then
    assertThat(allEvents).containsExactly(eventToStay).doesNotContain(eventToDelete);
  }

  @Test
  void shouldCreateComment() {
    // given
    EventDto eventToComment =
        eventService.createEvent(
            eventFixture()
                .title("Event to stay")
                .description("description")
                .startDate(LocalDate.of(2021, 9, 1))
                .endDate(LocalDate.of(2021, 2, 2))
                .build());
    CommentDto expected = commentFixture().createdDate(LocalDate.now()).build();

    // wehen
    CommentDto createdComment = commentService.createComment(eventToComment.getId(), expected);
    List<CommentDto> commentsOfEvent = commentService.getCommentsOfEvent(eventToComment.getId());
    // then
    assertThat(commentsOfEvent).containsExactly(createdComment);
    assertThat(createdComment).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
  }

  private EventDto.EventDtoBuilder eventFixture() {
    return EventDto.builder()
        .title("Test event")
        .description("Test event description")
        .status(EventState.PUBLISHED)
        .startDate(LocalDate.of(2022, 5, 15))
        .endDate(LocalDate.of(2022, 5, 16))
        .createdBy(userReadFixture().build());
  }

  private CommentDto.CommentDtoBuilder commentFixture() {
    return CommentDto.builder().body("Test comment body");
  }

  private UserReadDto.UserReadDtoBuilder userReadFixture() {
    return userReadDto.toBuilder();
  }
}

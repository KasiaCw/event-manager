package com.zdjavapol110.eventmanager.core.modules.event;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {

  @Autowired EventService eventService;

  @Autowired EventRepository eventRepository;

  @AfterEach
  void cleanup(){
    eventRepository.deleteAll();
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
    List<EventDto> allEvents = eventService.getAllEvents(pageNo, pageSize, sortBy, sortDir);

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
        EventDto.builder()
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
  @Disabled("implementation in progress")
  void shouldShearchByTitle() {
    // given
    EventDto matchingEvent =
        eventService.createEvent(eventFixture().title("matching aaa title").build());
    EventDto notMatchingEvent =
        eventService.createEvent(eventFixture().title("not matching bbb title").build());
    String searchString = "aaa";

    // when

    int pageNo = 0;
    int pageSize = 10;
    String sortBy = "startDate";
    String sortDir = "ASC";
    List<EventDto> matchingEvents =
        eventService.findByTitle(pageNo, pageSize, sortBy, sortDir, searchString);

    // then

    assertThat(matchingEvents).containsExactly(matchingEvent).doesNotContain(notMatchingEvent);
  }

  private EventDto.EventDtoBuilder eventFixture() {
    return EventDto.builder()
        .title("Test event")
        .description("Test event description")
        .startDate(LocalDate.of(2022, 5, 15))
        .endDate(LocalDate.of(2022, 5, 16));
  }
}

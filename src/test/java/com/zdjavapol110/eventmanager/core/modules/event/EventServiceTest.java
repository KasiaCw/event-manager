package com.zdjavapol110.eventmanager.core.modules.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {

  @Autowired EventService eventService;

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

  private EventDto.EventDtoBuilder eventFixture() {
    return EventDto.builder()
        .title("Test event")
        .description("Test event description")
        .startDate(LocalDate.of(2022, 5, 15))
        .endDate(LocalDate.of(2022, 5, 16));
  }
}

package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
class EventTestDataInitializer implements InitializingBean {
  private final EventService eventService;

  @Override
  public void afterPropertiesSet() {
    if (isEventsTableEmpty()) {
      log.info("Empty events DB - will initailize with test data");
      eventService.createEvent(
          EventDto.builder()
              .title("Metallica show")
              .description("Greatest show ever")
              .startDate(LocalDate.of(2022, 6, 15))
              .endDate(LocalDate.of(2022, 6, 16))
              .build());
      eventService.createEvent(
          EventDto.builder()
              .title("Queen show")
              .description("Greatest show ever")
              .startDate(LocalDate.of(2022, 7, 15))
              .endDate(LocalDate.of(2022, 7, 16))
              .build());
      eventService.createEvent(
          EventDto.builder()
              .title("Pink Floyd show")
              .description("Greatest show ever")
              .startDate(LocalDate.of(2022, 5, 15))
              .endDate(LocalDate.of(2022, 5, 16))
              .build());
      eventService.createEvent(
          EventDto.builder()
              .title("Black Sbbath show")
              .description("Greatest show ever")
              .startDate(LocalDate.of(2022, 3, 15))
              .endDate(LocalDate.of(2022, 3, 16))
              .build());
    }
  }

  private boolean isEventsTableEmpty() {
    return eventService.getAllEvents(0, 1, "startDate", "asc").isEmpty();
  }
}

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
  private final String eventDescriptionMock =
      "That dominion stars lights dominion divide years for fourth have don't stars is that\n"
          + "      he earth it first without heaven in placesdjiof seed it second morning saying.\n"
          + "    lorem ipsum That dominion stars lights dominion divide years for fourth have don't stars is that\n"
          + "      he earth it first without heaven in placesdjiof seed it second morning saying.\n"
          + "      lorem ipsumThat dominion stars lights dominion divide years for fourth have don't stars is that\n"
          + "      he earth it first without heaven in placesdjiof seed it second morning saying.\n"
          + "      lorem ipsumThat dominion stars lights dominion divide years for fourth have don't stars is that\n"
          + "      he earth it first without heaven in placesdjiof seed it second morning saying.\n"
          + "      lorem ipsumThat dominion stars lights dominion divide years for fourth have don't stars is that\n"
          + "      he earth it first without heaven in placesdjiof seed it second morning saying.\n"
          + "      lorem ipsum";

  @Override
  public void afterPropertiesSet() {
    if (isEventsTableEmpty()) {
      log.info("Empty events DB - will initailize with test data");
      for (int i = 0; i < 1; i++) {
        eventService.createEvent(
            EventDto.builder()
                .title("Metallica show" + i)
                .description("Greatest show ever")
                .startDate(LocalDate.of(2022, 6, 15))
                .endDate(LocalDate.of(2022, 6, 16))
                .status(EventState.PUBLISHED)
                .build());
        eventService.createEvent(
            EventDto.builder()
                .title("Queen show" + i)
                .description("Greatest show ever")
                .startDate(LocalDate.of(2022, 7, 15))
                .endDate(LocalDate.of(2022, 7, 16))
                .status(EventState.PUBLISHED)
                .build());
        eventService.createEvent(
            EventDto.builder()
                .title("Pink Floyd show" + i)
                .description("Greatest show ever")
                .startDate(LocalDate.of(2022, 5, 15))
                .endDate(LocalDate.of(2022, 5, 16))
                .status(EventState.PUBLISHED)
                .build());
        eventService.createEvent(
            EventDto.builder()
                .title("Black Sbbath show" + i)
                .description("Greatest show ever")
                .startDate(LocalDate.of(2022, 3, 15))
                .endDate(LocalDate.of(2022, 3, 16))
                .status(EventState.PUBLISHED)
                .build());
      }
    }
  }

  private boolean isEventsTableEmpty() {
    return eventService.getAllEvents(0, 1, "startDate", "asc").isEmpty();
  }
}

package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class EventTestDataInitializer implements InitializingBean {
    private final EventService eventService;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (isEventsTableEmpty()) {
            log.info("Empty events DB - will initailize with test data");
            eventService.createEvent(EventDto.builder()
                    .title("Metallica show")
                    .description("Greatest show ever")
                    .date("2022-05-30")
                    .build());
        }
    }

    private boolean isEventsTableEmpty() {
        return eventService.getAllEvents(0, 1).isEmpty();
    }


}

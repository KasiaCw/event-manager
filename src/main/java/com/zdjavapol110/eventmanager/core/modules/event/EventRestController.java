package com.zdjavapol110.eventmanager.core.modules.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class EventRestController {

  @Autowired EventService eventService;

  @ResponseBody
  @GetMapping("/api/events")
  Page<EventDto> getEvents(
      @RequestParam(value = "afterDate", required = false) LocalDate afterDate,
      @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
    if (afterDate == null) {
      afterDate = LocalDate.now();
    }
    return eventService.getAllEventsAfter(afterDate, pageNo, pageSize, "startDate", "asc");
  }

  @ResponseBody
  @GetMapping("/api/events/{id}")
  EventDto getEvents(@PathVariable("id") Long id) {
    return eventService.getEventById(id);
  }
}

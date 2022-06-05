package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;


  private EventDto shortenDescription(EventDto eventDto) {
    String shortenDescription = eventDto.getDescription();
    if (shortenDescription.length() > 50) {
      shortenDescription = shortenDescription.substring(0, 50);
    }
    return eventDto.toBuilder().description(shortenDescription).build();
  }

  @GetMapping("/event-form")
  public String showForm(Model model) {
    model.addAttribute("event",  EventDto.builder().status(EventState.PUBLISHED).build());
    model.addAttribute("statuses",EventState.values());
    return "events/new-event-form.html";
  }

  @PostMapping("/events")
  public String submit(
      @Valid @ModelAttribute("event") EventDto event, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      model.addAttribute("message", "Please enter correct details");
    } else {
      eventService.createEvent(event);
      return "redirect:/events";
    }
    model.addAttribute("statuses",EventState.values());
    model.addAttribute("event", event);
    return "events/new-event-form.html";
  }

  @GetMapping("/events/{id}")
  public String details(@PathVariable("id") Long id, Model model) {
    model.addAttribute("event", eventService.getEventById(id));
    return "events/event-details.html";
  }

  @GetMapping("/events")
  public String findEvents(@RequestParam(value = "keyword",required = false)   String keyword, Model model){
    System.out.println("Keyword:" + keyword);
    model.addAttribute("keyword", keyword);
    List<EventDto> eventsList;
    if (keyword != null) {
      eventsList= eventService.findByTitle(0, 10, "startDate", "asc", keyword);
    } else{
      eventsList= eventService.getAllEvents(0, 10, "startDate", "asc");
    }
    model.addAttribute("searchByKeywordFormView", "show");
    model.addAttribute(
            "events",
            eventsList.stream()
                    .map(this::shortenDescription)
                    .collect(Collectors.toList()));
    return "events/events-list.html";

  }

  @DeleteMapping("/events/{id}")
  public String delete (@PathVariable("id") Long id){
    eventService.deleteEvent(id);
    return "redirect:/events";
  }
}

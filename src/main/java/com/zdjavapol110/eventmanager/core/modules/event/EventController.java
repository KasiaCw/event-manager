package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;

  @GetMapping("/events")
  public String getEvents(Model model) {
    model.addAttribute(
        "events",
        eventService.getAllEvents(0, 10, "startDate", "asc").stream()
            .map(this::shortenDescription)
            .collect(Collectors.toList()));

    return "events/events-list.html";
  }

  private EventDto shortenDescription(EventDto eventDto) {
    String shortenDescription = eventDto.getDescription();
    if(shortenDescription.length()>50){
      shortenDescription = shortenDescription.substring(0,50);
    }
    return eventDto.toBuilder().description(shortenDescription).build();
  }

 @GetMapping( "/event-form")
  public String showForm(Model model){
      model.addAttribute("event", new EventDto());
    return "events/new-event-form.html";
 }

 @PostMapping( "/events")
  public String submit(@Valid @ModelAttribute("event")EventDto event,
                       BindingResult result, ModelMap model){
    if (result.hasErrors()){
      model.addAttribute("message","Pleas enter correct details");
    }
    eventService.createEvent(event);
    model.addAttribute("event",event);
    return "events/new-event-form.html";
 }
}

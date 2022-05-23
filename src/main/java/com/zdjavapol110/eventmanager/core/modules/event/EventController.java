package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public String getEvents(Model model){
        model.addAttribute("events", eventService.getAllEvents(0, 10));
        return "events/eventslist.html";
    }
}

package com.zdjavapol110.eventmanager.core.modules.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventRestController
{


    @Autowired
    EventService eventService;

    @ResponseBody
    @GetMapping("/api/events")
    List<EventDto> getEvents(){
        return eventService.getAllEvents(0,10000);
    }
}

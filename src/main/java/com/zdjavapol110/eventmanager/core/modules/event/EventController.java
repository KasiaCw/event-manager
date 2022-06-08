package com.zdjavapol110.eventmanager.core.modules.event;

import com.zdjavapol110.eventmanager.core.modules.event.comments.CommentDto;
import com.zdjavapol110.eventmanager.core.modules.event.comments.CommentService;
import com.zdjavapol110.eventmanager.core.modules.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EventController {

  private final EventService eventService;
  private final CommentService commentService;
  private final UserDetailsService userDetailsService;

  private EventDto shortenDescription(EventDto eventDto) {
    String shortenDescription = eventDto.getDescription();
    if (shortenDescription.length() > 50) {
      shortenDescription = shortenDescription.substring(0, 50);
    }
    return eventDto.toBuilder().description(shortenDescription).build();
  }

  @GetMapping("/event-form")
  public String showForm(Model model) {
    model.addAttribute("event", EventDto.builder().status(EventState.PUBLISHED).build());
    model.addAttribute("statuses", EventState.values());
    return "events/new-event-form.html";
  }

  @PostMapping("/events")
  public String submit(
      @Valid @ModelAttribute("event") EventDto event,
      BindingResult result,
      ModelMap model,
      HttpServletRequest request) {

    event.setCreatedBy(userDetailsService.getUserDetailsFromRequest(request).orElse(null));

    if (result.hasErrors()) {
      model.addAttribute("message", "Please enter correct details");
    } else {
      eventService.createEvent(event);
      return "redirect:/events";
    }
    model.addAttribute("statuses", EventState.values());
    model.addAttribute("event", event);
    return "events/new-event-form.html";
  }

  @GetMapping("/events/{id}")
  public String details(@PathVariable("id") Long id, Model model) {
    model.addAttribute("event", eventService.getEventById(id));
    model.addAttribute("newComment", new CommentDto());
    model.addAttribute("comments", commentService.getCommentsOfEvent(id));
    if (commentService.getCommentsOfEvent(id).size() == 0) {
      model.addAttribute("isZeroComments", "yes");
    } else {

      model.addAttribute("isZeroComments", "no");
    }
    return "events/event-details.html";
  }

  @GetMapping("/events")
  public String findEvents(
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      Model model) {
    System.out.println("Keyword:" + keyword);
    log.info("Keyword: {}, pageNo: {}, pageSize: {}", keyword, pageNo, pageSize);
    model.addAttribute("keyword", keyword);
    Page<EventDto> eventsPage;
    if (keyword != null) {
      eventsPage = eventService.findByTitle(pageNo, pageSize, "startDate", "asc", keyword);
    } else {
      eventsPage = eventService.getAllEvents(pageNo, pageSize, "startDate", "asc");
    }
    model.addAttribute("totalPages", eventsPage.getTotalPages());
    model.addAttribute("totalElements", eventsPage.getTotalElements());
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("searchByKeywordFormView", "show");
    model.addAttribute(
        "events",
        eventsPage.getContent().stream()
            .map(this::shortenDescription)
            .collect(Collectors.toList()));
    return "events/events-list.html";
  }

  @DeleteMapping("/events/{id}")
  public String delete(@PathVariable("id") Long id) {
    eventService.deleteEvent(id);
    return "redirect:/events";
  }
}

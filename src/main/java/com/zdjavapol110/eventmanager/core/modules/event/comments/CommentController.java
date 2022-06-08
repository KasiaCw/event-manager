package com.zdjavapol110.eventmanager.core.modules.event.comments;

import com.zdjavapol110.eventmanager.core.modules.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserDetailsService userDetailsService;

    @PostMapping(value="/events/{eventId}/comments",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
   public String createComment(@PathVariable(value = "eventId") Long eventId, CommentDto commentDto, HttpServletRequest request){
        commentDto.setCreatedBy(userDetailsService.getUserDetailsFromRequest(request).orElse(null));
        commentService.createComment(eventId,commentDto);
       return "redirect:/events/"+ eventId;
    }

}

package com.zdjavapol110.eventmanager.core.modules.event.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value="/events/{eventId}/comments",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
   public String createComment(@PathVariable(value = "eventId") Long eventId, CommentDto commentDto){
        commentService.createComment(eventId,commentDto);
       return "redirect:/events/"+ eventId;
    }

}

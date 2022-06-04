package com.zdjavapol110.eventmanager.core.modules.event.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/events/{eventId}/comments")
   public String createComment(@PathVariable(value = "eventId") Long eventId, @RequestBody CommentDto commentDto){
        commentService.createComment(eventId,commentDto);
       return "redirect:/events/"+ eventId;
    }

}

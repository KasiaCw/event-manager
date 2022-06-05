package com.zdjavapol110.eventmanager.core.modules.event.comments;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long eventId, CommentDto commentDto);
   List<CommentDto> getCommentsOfEvent(Long eventId);

}

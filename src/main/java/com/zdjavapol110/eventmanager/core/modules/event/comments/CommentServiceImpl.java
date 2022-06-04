package com.zdjavapol110.eventmanager.core.modules.event.comments;

import com.zdjavapol110.eventmanager.core.modules.event.Event;
import com.zdjavapol110.eventmanager.core.modules.event.EventRepository;
import com.zdjavapol110.eventmanager.core.modules.event.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{


    private CommentRepository commentRepository;
    private EventRepository eventRepository;

    public CommentServiceImpl(CommentRepository commentRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CommentDto createComment(Long eventId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Event event = eventRepository.findById(eventId).orElseThrow(
                ()->new ResourceNotFoundException("Event", "id", eventId));
        comment.setEvent(event);
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsOfEvent(Long eventId) {
        List<Comment> comments = commentRepository.findCommentsWithEventId(eventId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
    comment.setId(commentDto.getId());
    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());
    return comment;
    }
}

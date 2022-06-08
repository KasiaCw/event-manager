package com.zdjavapol110.eventmanager.core.modules.event.comments;

import com.zdjavapol110.eventmanager.core.modules.event.Event;
import com.zdjavapol110.eventmanager.core.modules.event.EventRepository;
import com.zdjavapol110.eventmanager.core.modules.event.ResourceNotFoundException;
import com.zdjavapol110.eventmanager.core.modules.userdetails.UserDetailsMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

  private final UserDetailsMapper userDetailsMapper;
  private CommentRepository commentRepository;
  private EventRepository eventRepository;

  public CommentServiceImpl(
      CommentRepository commentRepository,
      EventRepository eventRepository,
      UserDetailsMapper userDetailsMapper) {
    this.commentRepository = commentRepository;
    this.eventRepository = eventRepository;
    this.userDetailsMapper = userDetailsMapper;
  }

  @Override
  public CommentDto createComment(Long eventId, CommentDto commentDto) {
    Comment comment = mapToEntity(commentDto);
    comment.setCreatedDate(LocalDate.now());

    Event event =
        eventRepository
            .findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));
    comment.setEvent(event);
    Comment newComment = commentRepository.save(comment);

    return mapToDto(newComment);
  }

  @Override
  public List<CommentDto> getCommentsOfEvent(Long eventId) {
    List<Comment> comments = commentRepository.findCommentsWithEventId(eventId);
    return comments.stream().map(this::mapToDto).collect(Collectors.toList());
  }

  private CommentDto mapToDto(Comment comment) {
    CommentDto commentDto = new CommentDto();
    commentDto.setId(comment.getId());
    commentDto.setBody(comment.getBody());
    commentDto.setCreatedDate(comment.getCreatedDate());
    commentDto.setCreatedBy(userDetailsMapper.mapToUserDto(comment.getCreatedBy()));
    return commentDto;
  }

  private Comment mapToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setId(commentDto.getId());
    comment.setBody(commentDto.getBody());
    comment.setCreatedDate(comment.getCreatedDate());
    comment.setCreatedBy(userDetailsMapper.mapToUserEntity(commentDto.getCreatedBy()));
    return comment;
  }
}

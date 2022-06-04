package com.zdjavapol110.eventmanager.core.modules.event.comments;

import com.zdjavapol110.eventmanager.core.modules.event.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query("select c from Comment c where c.event.id = ?1")
  List<Comment> findCommentsWithEventId(Long id);
}

package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.model.Comment;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}

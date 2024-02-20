package com.programming.man.mdchat.mapper;

import com.programming.man.mdchat.dto.CommentsDto;
import com.programming.man.mdchat.model.Comment;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}

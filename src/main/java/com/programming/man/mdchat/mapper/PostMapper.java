package com.programming.man.mdchat.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.programming.man.mdchat.dto.PostRequest;
import com.programming.man.mdchat.dto.PostResponse;
import com.programming.man.mdchat.model.*;
import com.programming.man.mdchat.repository.CommentRepository;
import com.programming.man.mdchat.repository.VoteRepository;
import com.programming.man.mdchat.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.programming.man.mdchat.model.VoteType.DOWNVOTE;
import static com.programming.man.mdchat.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

//    @Mapping(target = "created", expression = "java(java.time.Instant.now())")
//    @Mapping(target = "description", source = "postRequest.description")
//    @Mapping(target = "channel", source = "channel")
//    @Mapping(target = "voteCount", constant = "0")
//    @Mapping(target = "user", source = "user")
//    @Mapping(target = "id", source = "postRequest.postId")
//    @Mapping(target = "commentsLocked", source = "postRequest.commentsLocked")
//    public abstract Post map(PostRequest postRequest, Channel channel, User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "channelName", source = "channel.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        if (post == null || post.getCreated() == null)
            return null;
        else
            return TimeAgo.using(post.getCreated().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
//            Optional<Vote> voteForPostByUser =
//                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
//            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }

}
package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDto {
    private Long postId;
    private Long commentId;
    private Long voteCount;
    private Integer lastVoteType;
    private Boolean upVoted;
    private Boolean downVoted;
    private Integer id;
    private String message;
}

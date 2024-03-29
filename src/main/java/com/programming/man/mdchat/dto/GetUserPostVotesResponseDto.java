package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPostVotesResponseDto {
    private Long postId;
    private Long commentId;
    private boolean isVoted;
    private Long countVotes;
    private boolean upVoted;
    private boolean downVoted;
    private String momentVoted;
}

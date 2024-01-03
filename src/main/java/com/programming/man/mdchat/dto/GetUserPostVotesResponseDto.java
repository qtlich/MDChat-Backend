package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPostVotesResponseDto {
    private Long countVotes;
    private boolean upVote;
    private boolean downVote;
}

package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestV1Dto {
    private Long postId;
    private Long commentId;
    private Integer voteType;
}

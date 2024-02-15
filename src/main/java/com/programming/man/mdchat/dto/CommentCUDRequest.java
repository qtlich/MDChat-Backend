package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCUDRequest {
    private Integer operationType;
    private Long commentId;
    private Long parentId;
    private Long postId;
    private String comment;
}

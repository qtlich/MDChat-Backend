package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCUDRequest {
    private Integer operationType;
    private Long postId;
    private Long channelId;
    private String postName;
    private String postDescription;
    private String url;
    private Boolean commentsLocked;
}

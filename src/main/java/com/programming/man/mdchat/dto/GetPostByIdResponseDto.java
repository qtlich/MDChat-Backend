package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostByIdResponseDto {
    private Long postId;
    private Long channelId;
    private Short channelType;
    private String channelName;
    private String channelDescription;
    private Long userId;
    private String userName;
    private String postName;
    private String postDescription;
    private String url;
    private Integer votesCount;
    private Long commentsCount;
    private String created;
    private String modified;
    private String createdTimeAgo;
    private String modifiedTimeAgo;
    private Boolean commentsLocked;
    private Boolean canDelete;
    private Boolean canEdit;
}
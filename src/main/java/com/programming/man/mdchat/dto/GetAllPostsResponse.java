package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostsResponse {
    private Long postId;
    private Long userId;
    private Long postChannelId;
    private Short channelType;
    private String channelName;
    private String channelDescription;
    private Long postUserId;
    private String postUserName;
    private String postName;
    private String postDescription;
    private Integer postVoteCount;
    private String postCreated;
    private String postModified;
    private String postUrl;
    private Integer currentUserVoteType;
    private Long postCountComments;
    private String postTimeAgo;
    private Boolean canEdit;
    private Boolean canDelete;
}

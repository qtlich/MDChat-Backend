package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserPostsUniversalResponse {
    private Long postId;
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
    private String lastVisited;
    private Boolean hidden;
    private Boolean saved;
    private Boolean canEdit;
    private Boolean canDelete;
}

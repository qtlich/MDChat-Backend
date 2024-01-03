package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostsDto {
    private Long postId;
    private Long userId;
    private Long postChannelId;
    private Short channelType;
    private String channelName;
    private String channelDescription;
    private Long postUseId;
    private String postUserName;
    private String postName;
    private String postDescription;
    private Integer postVoteCount;
    private String postCreated;
    private String postUrl;
    private Integer currentUserVoteType;
    private Integer postCountComments;
    private String postTimeAgo;
}

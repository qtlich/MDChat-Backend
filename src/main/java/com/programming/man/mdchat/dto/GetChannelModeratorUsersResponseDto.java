package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetChannelModeratorUsersResponseDto {
    private Long id;
    private Long channelId;
    private Short channelTypeId;
    private String channelName;
    private Long userId;
    private String userName;
    private Long authorId;
    private String authorName;
    private String created;
    private String createdTimeAgo;
    private Boolean canViewChannel;
    private Boolean canViewPosts;
    private Boolean canCreatePosts;
    private Boolean canComment;
    private Boolean canVote;
    private Boolean isChannelModerator;
    private Boolean isAdministrator;
}

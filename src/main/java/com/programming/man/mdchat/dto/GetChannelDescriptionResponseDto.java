package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChannelDescriptionResponseDto {
    private Long channelId;
    private Short channelType;
    private Long userId;
    private String userName;
    private String channelName;
    private String channelDescription;
    private String created;
    private String modified;
    private Long countPosts;
    private String createdTimeAgo;
    private Boolean isDeleted;
    private Boolean isSubscribedOnChannel;
    private Boolean hasAccessToChannel;
    private Boolean isChannelModerator;
    private Boolean canEdit;
    private Boolean canDelete;
}

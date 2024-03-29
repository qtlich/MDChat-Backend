package com.programming.man.mdchat.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchChannelsResponseDto {
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

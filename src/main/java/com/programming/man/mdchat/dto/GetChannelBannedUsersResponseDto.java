package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChannelBannedUsersResponseDto {
    private Long id;
    private Long channelId;
    private Short channelTypeId;
    private String channelName;
    private Long userId;
    private String userName;
    private Long banReasonId;
    private String banReasonName;
    private String note;
    private Short daysBanned;
    private Boolean permanentBanned;
    private Long authorId;
    private String authorName;
    private String created;
}

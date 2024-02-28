package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageUserChannelBanningRequestDto {
    private Long userId;
    private Long channelId;
    private Long banReasonId;
    private String note;
    private Short daysBanned;
    private Boolean permanentBanned;
}

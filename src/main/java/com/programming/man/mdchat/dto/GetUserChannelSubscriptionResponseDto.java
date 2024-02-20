package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserChannelSubscriptionResponseDto {
    private Long userId;
    private Long channelId;
    private Boolean isSubscribed;

}

package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetChannelCountSubscribersResponseDto {
    private Long channelId;
    private Long countSubscribers;
}

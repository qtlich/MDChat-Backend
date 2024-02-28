package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChannelBannedUsersRequestDto {
    private Long channelId;
    private Long offset;
    private Long limit;
}

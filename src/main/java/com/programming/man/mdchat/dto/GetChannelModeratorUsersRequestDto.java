package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetChannelModeratorUsersRequestDto {
    private Long channelId;
    private Long offset;
    private Long limit;

}

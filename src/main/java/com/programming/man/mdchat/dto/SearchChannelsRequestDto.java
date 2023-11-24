package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchChannelsRequestDto {
    private String channelName;
    private Long descLength;
    private Short searchMode;
}

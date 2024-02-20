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
    private Long userId;
    private String channelName;
    private Integer maxNameLength;
    private Integer maxDescriptionLength;
    private Integer limit;
}

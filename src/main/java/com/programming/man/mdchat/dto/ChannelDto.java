package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelDto {
    private Long id;
    private String name;
    private String description;
    private Short channelType;
    private Integer numberOfPosts;
}

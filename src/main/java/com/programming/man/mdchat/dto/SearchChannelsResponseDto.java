package com.programming.man.mdchat.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchChannelsResponseDto {// extends ChannelDto{
    private Long id;
    private String name;
    private String description;
    private Short channelType;
    private Integer numberOfPosts;
}

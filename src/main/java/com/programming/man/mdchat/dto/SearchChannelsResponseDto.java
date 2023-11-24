package com.programming.man.mdchat.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchChannelsResponseDto {
    private Long id;
    private String channelName;
    private String channelDescription;
    private Short channelType;
    private String created;
    private String author;
    private Long countPosts;
}

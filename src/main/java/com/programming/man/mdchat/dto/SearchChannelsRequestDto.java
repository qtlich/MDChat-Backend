package com.programming.man.mdchat.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchChannelsRequestDto {
    private String channelName;
}

package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelCUDRequest {
    private Integer operationType;
    private Long channelId;
    private Short channelTypeId;
    private String channelName;
    private String channelDescription;
    private Boolean deleted;
}

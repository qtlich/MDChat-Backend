package com.programming.man.mdchat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;
    private Long channelId;
    private String channelName;
    private String url;
    private String description;
}

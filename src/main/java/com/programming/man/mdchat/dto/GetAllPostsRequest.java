package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPostsRequest {
    private String sortMode;
    private Long offset;
    private Long limit;
    private Long postNameMaxLength;
    private Long postDescriptionMaxLength;

}

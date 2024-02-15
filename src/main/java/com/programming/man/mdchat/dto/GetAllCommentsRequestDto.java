package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCommentsRequestDto {
    private Long commentId;
    private Long postId;
    private String sortMode;
    private Long offset;
    private Long limit;
    private Long commentMaxLength;
    private Boolean showDeleted;
}

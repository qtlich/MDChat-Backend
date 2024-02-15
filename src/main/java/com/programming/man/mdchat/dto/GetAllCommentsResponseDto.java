package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCommentsResponseDto {
    private Long id;
    private Long parentId;
    private String comment;
    private Long postId;
    private Long userId;
    private String userName;
    private Integer voteCount;
    private Integer claimsCount;
    private Boolean commentsClosed;
    private String created;
    private String modified;
    private String createdTimeAgo;
    private String modifiedTimeAgo;
    private Boolean canDelete;
    private Boolean canEdit;
    private Boolean deleted;
}

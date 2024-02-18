package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserCommentsUniversalResponse {
    private Long commentId;
    private Long commentParentId;
    private Long channelId;
    private String channelName;
    private Long postId;
    private String postName;
    private String postDescription;
    private Long commentUserId;
    private String commentUserName;
    private String commentText;
    private String commentCreated;
    private String commentModified;
    private Integer commentVoteCount;
    private Boolean commentsClosed;
    private Integer commentClaimsCount;
    private String commentTimeAgo;
    private Boolean canEdit;
    private Boolean canDelete;
    private Boolean isDeleted;
}

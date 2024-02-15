package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String text;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @NotNull
    @Builder.Default
    private Integer claimsCount = 0;
    @NotNull
    @Builder.Default
    private Integer voteCount = 0;
    @NotNull
    private Instant created;
    private Instant modified;
    @NotNull
    @Builder.Default
    private boolean commentsClosed = false;
    private Boolean deleted = false;
}

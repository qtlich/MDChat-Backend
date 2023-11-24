package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @NotNull
    @Lob
    @Column(columnDefinition = "TEXT")
    private String postName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @NotNull
    @Transient
    @Access(AccessType.PROPERTY)
    private Instant createdDate;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelId", referencedColumnName = "id")
    private Channel channel;
    @NotNull
    @Builder.Default
    private Integer voteCount = 0;
    //    private Long parentPostId;
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        createdDate = Instant.now();
    }

}

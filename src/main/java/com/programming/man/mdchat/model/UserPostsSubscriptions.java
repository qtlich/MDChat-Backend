package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPostsSubscriptions {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    @NotNull
    @Transient
    @Access(AccessType.PROPERTY)
    private Instant createdDate;
    @NotNull
    @Builder.Default
    private boolean enabled = true;
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        createdDate = Instant.now();
    }

}

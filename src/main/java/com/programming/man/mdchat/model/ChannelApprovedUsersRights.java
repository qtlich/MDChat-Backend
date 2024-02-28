package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelApprovedUsersRights {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channelId", referencedColumnName = "id")
    private Channel channel;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private User author;
    @NotNull
    private Instant created;
    private boolean canViewChannel;
    private boolean canViewPosts;
    private boolean canCreatePosts;
    private boolean canVote;
    private boolean canComment;
    private boolean isChannelModerator;
    private boolean isAdministrator;
}

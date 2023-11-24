package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private Short channelType; // 1 - restricted, 2 - private
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channelId", referencedColumnName = "id")
    private Channel channel;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private boolean canView;
    private boolean canPost;
    private boolean canComment;
}

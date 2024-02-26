package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelPermissions {
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
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "permissionId", referencedColumnName = "id")
    private Permission permission;
    @NotNull
    private Instant created;
}

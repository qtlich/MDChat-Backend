package com.programming.man.mdchat.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannedUsers {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelId", referencedColumnName = "id")
    private Channel channel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banReasonId", referencedColumnName = "id")
    private BanReason banreason;
    private String note;
    private Short daysBanned;
    @NotNull
    private Instant created;
}

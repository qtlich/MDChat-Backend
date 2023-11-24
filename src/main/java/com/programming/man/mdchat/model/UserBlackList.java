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
public class UserBlackList {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdFrom", referencedColumnName = "userId")
    private User userFrom;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdTo", referencedColumnName = "userId")
    private User userTo;
    @NotNull
    @Transient
    @Access(AccessType.PROPERTY)
    private Instant momentBan;
    @NotNull
    private boolean disableView;
    @NotNull
    private boolean disableComment;
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        momentBan = Instant.now();
    }
}

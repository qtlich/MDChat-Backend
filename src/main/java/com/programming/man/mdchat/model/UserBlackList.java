package com.programming.man.mdchat.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

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
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdFrom", referencedColumnName = "id")
    private User userFrom;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdTo", referencedColumnName = "id")
    private User userTo;
    @NotNull
    private Instant created;
    @NotNull
    private boolean disableView;
    @NotNull
    private boolean disableComment;

}

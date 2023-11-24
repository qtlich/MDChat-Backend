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
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String token;
    @NotNull
    @Transient
    @Access(AccessType.PROPERTY)
    private Instant createdDate;
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        createdDate = Instant.now();
    }
}

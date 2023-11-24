package com.programming.man.mdchat.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String email;
    @NotNull
    @Transient
    @Access(AccessType.PROPERTY)
    private Instant created;
    @NotNull
    @Builder.Default
    private boolean enabled = true;
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        created = Instant.now();
    }
}

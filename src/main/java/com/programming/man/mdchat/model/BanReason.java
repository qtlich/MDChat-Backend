package com.programming.man.mdchat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanReason {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private String name;
}

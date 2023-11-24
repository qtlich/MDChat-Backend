package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.java.Log;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Channel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NonNull
    @Lob
    @Column(name = "name", columnDefinition = "CHAR(30)")
    private String name;
    @NotNull
    @Lob
    @Column(name = "description",columnDefinition = "LONGTEXT")
    private String description;
    @NotNull
    private Short channelType;
    @OneToMany(fetch = LAZY)
    @Column(name = "post_id")
    private List<Post> posts;
    @NotNull
    @Transient
    @Access(AccessType.PROPERTY)
    private Instant createdDate;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        createdDate = Instant.now();
    }
}

package com.programming.man.mdchat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Channel implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NonNull
    @Lob
    @Column(name = "name", columnDefinition = "CHAR(30)")
    private String name;
    @NotNull
    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @NotNull
    private Short channelType;
    @OneToMany(fetch = LAZY)
    @Column(name = "post_id")
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Post> posts;
    @NotNull
    private Instant created;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    private Boolean deleted;
    private Boolean modified;
}

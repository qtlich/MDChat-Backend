package com.programming.man.mdchat.model;

import com.programming.man.mdchat.dto.OperationResultDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Lob
    @Column(columnDefinition = "TEXT")
    private String postName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @NotNull
    private Instant created;
    private Instant modified;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelId", referencedColumnName = "id")
    private Channel channel;
    @NotNull
    @Builder.Default
    private Integer voteCount = 0;
    @NotNull
    private Boolean commentsLocked;
}

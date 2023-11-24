package com.programming.man.mdchat.model;

import com.programming.man.mdchat.dto.OperationResultDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;


@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "procDeletePost",
                procedureName = "deletePost",
                resultClasses = {OperationResultDto.class},
                parameters = {
                        @StoredProcedureParameter(
                                name = "postId",
                                type = Long.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "userId",
                                type = Long.class,
                                mode = ParameterMode.IN)
                }

        )
})
@Getter
@Setter
@Entity
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

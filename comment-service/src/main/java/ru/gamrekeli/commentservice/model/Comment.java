package ru.gamrekeli.commentservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Comment {

    @Id
    @SequenceGenerator(
            name = "sequence_comment",
            sequenceName = "sequence_comment",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_comment"
    )
    private Long commentId;
    @Column(
            name = "text",
            nullable = false
    )
    private String text;

    @Column(
            name = "author",
            nullable = false
    )
    private Long authorId;

    @Column(
            name = "blog",
            nullable = false
    )
    private Long blogId;
}

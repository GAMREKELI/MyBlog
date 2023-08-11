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
            nullable = false
    )
    private String text;

    private Long authorId;
    private Long blogId;
}

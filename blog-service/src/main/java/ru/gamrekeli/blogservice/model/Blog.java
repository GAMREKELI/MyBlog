package ru.gamrekeli.blogservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(
        name = "blogs"
)
public class Blog {

    @Id
    @SequenceGenerator(
            name = "sequence_blog",
            sequenceName = "sequence_blog"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_blog"
    )
    private Long blogId;

    private String title;
    private String content;

    @Column(
            name = "author",
            nullable = false
    )
    private Long authorId;
}

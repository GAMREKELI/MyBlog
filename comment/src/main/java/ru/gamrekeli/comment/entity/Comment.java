package ru.gamrekeli.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.gamrekeli.comment.entity.User.User;

import java.util.Date;

@Setter
@Getter
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long commentId;
    private String content;
    private Date createAt;

    @ManyToOne
    private User author;
}

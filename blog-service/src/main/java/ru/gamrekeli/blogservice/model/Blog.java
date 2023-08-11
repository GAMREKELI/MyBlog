package ru.gamrekeli.blogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Blog {

    @Id
    private Long blogId;
    private String title;
    private String content;
    private Long authorId;
}

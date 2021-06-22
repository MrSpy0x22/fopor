package pl.fopor.serwis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer commentId;

    @Size(min = 8 , max = 512 , message = "To pole musi zwierać od {min} do {max} znaków")
    String commentContent;

    @CreationTimestamp
    LocalDateTime commentCreationTime;

    @OneToOne
    @JoinColumn(name = "fk_comment_author")
    User commentAuthor;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_comment_post")
    Post commentPost;

}

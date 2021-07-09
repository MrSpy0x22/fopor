package pl.fopor.serwis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @JsonFormat(pattern="HH:mm:ss, dd/MM/yyyy")
    @CreationTimestamp
    LocalDateTime commentCreationTime;

    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_comment_author")
    User commentAuthor;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_comment_post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Post commentPost;

    public void removeCommentPost(Post post) {
        if (post != null) {
            post.getPostComments().remove(this);
            this.setCommentPost(null);
        }
    }
}

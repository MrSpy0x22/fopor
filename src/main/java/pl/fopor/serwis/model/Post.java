package pl.fopor.serwis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer postId;

    @Size(min = 3 , max = 64 , message = "To pole musi zawierać od {min} do {max} znaków")
    String postTitle;

    @Size(min = 16 , max = 1024 , message = "To pole musi zwierać od {min} do {max} znaków")
    String postContent;

    @CreationTimestamp
    LocalDateTime postCreationTime;

    @Enumerated(EnumType.ORDINAL)
    ContentState postState;

    Boolean postSolved;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_post_author")
    User postAuthor;

    @OneToOne(optional = false)
    @JoinColumn(name = "fk_post_category")
    Category postCategory;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_post_comments")
    List<Comment> postComments;

}

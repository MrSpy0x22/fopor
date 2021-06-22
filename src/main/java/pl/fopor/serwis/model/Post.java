package pl.fopor.serwis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @CreationTimestamp
    LocalDateTime postLstEditTime;

    @Enumerated(EnumType.ORDINAL)
    ContentState postState;

    Boolean postSolved;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_post_author")
    User postAuthor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_post_category")
    Category postCategory;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_post_comments")
    List<Comment> postComments;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL , mappedBy = "userFollowedPosts")
    Set<User> postFollowedBy = new HashSet<>();

}

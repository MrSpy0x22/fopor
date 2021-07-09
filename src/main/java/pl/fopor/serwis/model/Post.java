package pl.fopor.serwis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    @JsonFormat(pattern="HH:mm:ss, dd/MM/yyyy")
    @CreationTimestamp
    LocalDateTime postCreationTime;

    @JsonFormat(pattern="HH:mm:ss, dd/MM/yyyy")
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

    @JsonIgnoreProperties({"commentAuthor" , "commentPost"})
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)// , orphanRemoval = true)
    @JoinColumn(name = "fk_post_comments")
    List<Comment> postComments;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL , mappedBy = "userFollowedPosts")
    Set<User> postFollowedBy = new HashSet<>();
}

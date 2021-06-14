package pl.fopor.serwis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.fopor.serwis.app.validator.Email;
import pl.fopor.serwis.app.validator.PhoneNumber;
import pl.fopor.serwis.app.validator.UserName;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;

    @Size(min = 3 , max = 128 , message = "Podaj wartość z przedziału {min}..{max}")
    @NotNull(message = "Hasło jest polem obowiązkowym")
    String userPassword;

    String userRole;

    @Column(unique = true)
    @Size(min = 2 , max = 32 , message = "To pole musi zawierać {min}-{max} znaków.")
    @NotNull(message = "Nazwa użytkownika jest polem obowiązkowym")
    @UserName(message = "Nieprawidłowa nazwa użytkownika")
    String userName;

    @Column(unique = true)
    @Size(min = 5 , max = 64 , message = "To pole musi zawierać {min}-{max} znaków.")
    @NotNull(message = "E-mail jest polem obowiązkowym")
    @Email(message = "Nieprawidłowy e-mail")
    String userMail;

    Boolean userEnabled;

    @CreationTimestamp
    LocalDateTime userJoinTime;

    @JsonIgnore
    @OneToMany(mappedBy = "postAuthor")
    List<Post> userPosts;

    @JsonIgnore
    @OneToMany
    List<Category> createdCategories;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_followed_posts" ,
            joinColumns = @JoinColumn(name = "follower_id" , referencedColumnName = "userId") ,
            inverseJoinColumns = @JoinColumn(name = "followed_post_id" , referencedColumnName = "postId") )
    Set<Post> userFollowedPosts = new HashSet<>();

    public void addFollowedPost(Post post) {
        if (post != null) {
            this.getUserFollowedPosts().add(post);
            post.getPostFollowedBy().add(this);
        }
    }

    public void removeFollowedPost(Post post) {
        if (post != null) {
            this.getUserFollowedPosts().remove(post);
            post.getPostFollowedBy().remove(this);
        }
    }

    public String toString() {
        return this.userName;
    }
}

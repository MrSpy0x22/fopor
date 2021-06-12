package pl.fopor.serwis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.fopor.serwis.app.validator.Email;
import pl.fopor.serwis.app.validator.PhoneNumber;
import pl.fopor.serwis.app.validator.UserName;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @OneToMany(mappedBy = "postAuthor")
    List<Post> userPosts;

    @OneToMany
    List<Category> createdCategories;

}

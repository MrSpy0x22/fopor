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

    @NotEmpty(message = "Error")
    @NotNull(message = "Ranga jest polem obowiązkowym")
    String userRole;

    @Column(unique = true)
    @NotNull(message = "Nazwa użytkownika jest polem obowiązkowym")
    @UserName(message = "Nieprawidłowa nazwa użytkownika")
    String userName;

    @Column(unique = true)
    @Size(max = 64 , message = "Podano za dużo znaków")
    @NotNull(message = "E-mail jest polem obowiązkowym")
    @Email(message = "Nieprawidłowy e-mail")
    String userMail;

    Boolean userEnabled;

    @Column(unique = true)
    @Size(max = 16 , message = "Podano za dużo znaków")
    @PhoneNumber(message = "Nieprawidłowy format numeru telefonu")
    String userPhoneNumber;

    @CreationTimestamp
    LocalDateTime userJoinTime;

    @OneToMany
    List<Category> createdCategories;

}

package pl.fopor.serwis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer categoryId;

    @Column(unique = true , nullable = false)
    @NotNull(message = "Nie podano nazwy kategorii")
    @Size(min = 2 , max = 32 , message = "Podaj wartość z przedziału {min}..{max}")
    String categoryName;

    @NotNull(message = "To pole jest wymagane")
    String categoryAccent;

    @NotNull(message = "To pole jest wymagane")
    String categoryIcon;

    @CreationTimestamp
    LocalDateTime categoryCreationTime;

    @ManyToOne(optional = false , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_category_creator")
    User categoryCreator;

    @JsonIgnore
    @OneToMany(mappedBy = "postCategory")
    List<Post> categoryPosts;

    @Override
    public String toString() {
        return this.categoryName;
    }
}

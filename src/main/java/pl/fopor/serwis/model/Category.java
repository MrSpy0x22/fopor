package pl.fopor.serwis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer categoryId;

    @Column(unique = true , nullable = false)
    @NotNull(message = "Nie podano nazwy kategorii")
    @Size(min = 2 , max = 32 , message = "Podaj wartość z przedziału {min}..{max}")
    String categoryName;

    @NotNull(message = "To pole jest wymagane")
    long categoryAccent;

    @NotNull(message = "To pole jest wymagane")
    String categoryIcon;

    @CreationTimestamp
    LocalDateTime categoryCreationTime;

    // TODO temporary allow null
    //@ManyToOne(optional = false , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @ManyToOne(optional = false , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_category_creator")
    User categoryCreator;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_category_post")
    List<Post> categoryPosts;

    @Override
    public String toString() {
        return this.categoryName;
    }
}

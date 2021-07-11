package pl.fopor.serwis;

import org.hibernate.boot.model.relational.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.fopor.serwis.model.*;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.CommentService;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin" , password = "admin")
class SerwisApplicationTests {

    @MockBean private UserService userService;
    @MockBean private CategoryService categoryService;
    @MockBean private PostService postService;
    @MockBean private CommentService commentService;

    @Autowired private MockMvc mockMvc;
    @Mock private Database database;

    @Test
    void createCategory() {
        User user = new User(1 , new BCryptPasswordEncoder().encode("qwerty") ,
                UserRole.USER , "Tester" , "test@com.pl" , true ,
                LocalDateTime.now() , null , null , null);
        User userForComment = new User(2 , new BCryptPasswordEncoder().encode("qwerty") ,
                UserRole.USER , "Komentator" , "test2@com.pl" , true ,
                LocalDateTime.now() , null , null , null);
        Category category = new Category(1 , "Sport" , "0" , "sport" , LocalDateTime.now() , null , null);
        Post post = new Post(1 , "Test" , "content" , LocalDateTime.now() , LocalDateTime.now() ,
                ContentState.CS_NORMAL , false , null , null , null , null);
        Comment comment = new Comment(1 , "komentarz" , LocalDateTime.now() , null , null);

        // Dodawanie użytkownika do postu
        post.setPostAuthor(user);
        user.setUserPosts(List.of(post));

        post.setPostCategory(category);
        category.setCategoryPosts(List.of(post));

        post.setPostComments(List.of(comment));
        comment.setCommentPost(post);

        comment.setCommentAuthor(userForComment);

        category.setCategoryCreator(userForComment);

        userService.save(user);
        userService.save(userForComment);
        categoryService.save(category);
        postService.save(post);
        commentService.save(comment);

        Assertions.assertTrue(post.getPostComments().contains(comment));
    }

}

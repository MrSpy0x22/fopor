package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.model.ContentState;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ViewPost {

    private final PostService postService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ViewPost(PostService postService, CategoryService categoryService, UserService userService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/posts")
    public String getPage(Model model ) {
        List<Post> users = postService.getAll();

        //model.addAttribute("catList" , users);
        return "error";
    }

    @GetMapping(path = "/post")
    public String getForm(@RequestParam(name = "id" , required = false) Integer id , Model model) {
        Post post;

        if (id != null) {
            post = postService.getId(id).orElse(null);
        } else {
            post = new Post();
        }

        model.addAttribute("post" , post);
        return "writePost";
    }

    @GetMapping(path = "/write")
    public String getWritePage(Model model , @RequestParam(name = "id" , required = false) Integer id) {
        Post post;

        if (id == null) {
            post = new Post();
        } else {
            Optional<Post> opt = postService.getId(id);
            if (opt.isEmpty()) {
                return "redirect:/error";
            } else {
                post = opt.get();
            }
        }

        // Pobieranie wszystkich kategorii
        var categories = categoryService.getAll()
                .stream()
                .sorted(Comparator.comparing(Category::getCategoryName))
                .collect(Collectors.toList());

        model.addAttribute("categories" , categories);
        model.addAttribute("post" , post);

        return "writePost";
    }

    @PostMapping(path = "/write")
    public String postWrite(@ModelAttribute @Valid Post post , BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "writePost";
        } else {
            try {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String uname = ((UserDetails) principal).getUsername();
                User user = userService.getByName(uname);

                if (user == null) {
                    return "redirect:/error";
                } else {
                    post.setPostAuthor(user);
                    post.setPostSolved(false);
                    post.setPostState(ContentState.CS_NORMAL);
                    postService.save(post);
                }
            } catch (HttpStatusCodeException e) {
                bindResult.rejectValue(null , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
                return "writePost";
            }

            // Redirect to post
            return "redirect:/unresolved";
        }
    }
}

package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
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
import pl.fopor.serwis.Utils.FoPorDataUtils;
import pl.fopor.serwis.Utils.FoPorUtils;
import pl.fopor.serwis.model.*;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ViewCategories {

    private final CategoryService categoryService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public ViewCategories(CategoryService categoryService, UserService userService, PostService postService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/categories")
    public String getPage(Model model ) {
        List<Category> cats = categoryService.getAll();

        String uname = FoPorUtils.getActiveUserName();
        UserRole urole = uname == null ? UserRole.NONE : userService.getByName(uname).getUserRole();

        model.addAttribute("catList" , cats);
        model.addAttribute("admin" , urole.equals(UserRole.ADMIN));
        return "allCategories";
    }

    @GetMapping(path = "/category")
    public String getForm(@RequestParam(name = "id" , required = false) Integer id , Model model) {
        Category category;

        if (id != null) {
            category = categoryService.getId(id).orElse(null);

            if (category == null) {
                return "messages/not_found";
            }
        } else {
            category = new Category();
        }

        model.addAttribute("icons" , FoPorDataUtils.iconNames);
        model.addAttribute("colors" , FoPorDataUtils.colorsMap);
        model.addAttribute("category" , category);
        return "popups/p_catForm";
    }

    @PostMapping(path = "/category")
    public String postCategory(@ModelAttribute @Valid Category category , BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "popups/p_catForm";
        } else {
            try {
                User user = userService.getByName(FoPorUtils.getActiveUserName());

                if (user == null) {
                    return "redirect:/access_denied";
                } else {
                    category.setCategoryCreator(user);
                    categoryService.save(category);
                }
            } catch (HttpStatusCodeException e) {
                bindResult.rejectValue("error" , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
                System.out.println("Exception catched: " + e.getMessage());
                return "popups/p_catForm";
            }

            return "redirect:/success";
        }
    }

    @PostMapping(path = "/category" , params = "delete")
    @Transactional
    public String deleteCategory(@ModelAttribute Category category) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname = ((UserDetails) principal).getUsername();
        User user = userService.getByName(uname);

        User creator = category.getCategoryCreator();

        if (creator != null) {
            creator.removeCreatedCategory(category);
            userService.save(creator);
        }

        // Usuwanie wszystkich postów w kategorii
        var posts = category.getCategoryPosts();
        if (posts != null) {
            for (Post post : posts) {
                postService.deleteId(post.getPostId());
            }
        }

        if (user.getUserRole() == UserRole.ADMIN) {
            categoryService.deleteId(category.getCategoryId());
            return "redirect:/deleted";
        } else {
            return "redirect:/access_denied";
        }
    }
}

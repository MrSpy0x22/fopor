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
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.model.ContentState;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ViewCategories {

    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ViewCategories(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/categories")
    public String getPage(Model model ) {
        List<Category> cats = categoryService.getAll();

        model.addAttribute("catList" , cats);
        return "allCategories";
    }

    @GetMapping(path = "/category")
    public String getForm(@RequestParam(name = "id" , required = false) Integer id , Model model) {
        Category category;

        if (id != null) {
            category = categoryService.getId(id).orElse(null);
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
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String uname = ((UserDetails) principal).getUsername();
                User user = userService.getByName(uname);

                if (user == null) {
                    return "redirect:/error";
                } else {
                    category.setCategoryCreator(user);
                    categoryService.save(category);
                }
            } catch (HttpStatusCodeException e) {
                bindResult.rejectValue(null , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
                return "popups/p_catForm";
            }

            // Redirect to post
            return "redirect:/categories";
        }
    }
}

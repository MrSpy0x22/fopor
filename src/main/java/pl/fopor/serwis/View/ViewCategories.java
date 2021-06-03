package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.service.CategoryService;

import java.util.List;

@Controller
public class ViewCategories {

    private final CategoryService categoryService;

    @Autowired
    public ViewCategories(CategoryService categoryService) {
        this.categoryService = categoryService;
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

        model.addAttribute("category" , category);
        return "popups/p_catForm";
    }
}

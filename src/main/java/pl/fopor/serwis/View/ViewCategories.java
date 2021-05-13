package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String getPage(Model model) {
        List<Category> cats = categoryService.getAll();

        model.addAttribute("catList" , cats);
        return "allCategories";
    }
}

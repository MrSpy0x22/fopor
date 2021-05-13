package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.service.CategoryService;

@Controller
public class ViewAddCategory {
    public CategoryService categoryService;

    @Autowired
    public ViewAddCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/addcategory")
    public String getPage(Model model) {
        model.addAttribute("category" , new Category());

        return "addCategory";
    }

    @PostMapping("/addcategory")
    public String addNewGategory(@ModelAttribute Category category , Model model) {
        if (category != null) {
            categoryService.save(category);
        }

        model.addAttribute("category" , category);

        return "redirect:addcategory";
    }
}

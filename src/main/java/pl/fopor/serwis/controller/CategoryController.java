package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.service.CategoryService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController implements ControllerTpl<Category> {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    @Override
    public Category getId(@PathVariable("id") Integer id) {
        return categoryService.getId(id).orElse(null);
    }

    @GetMapping("/all")
    @Override
    public Page<Category> getPageOf(Pageable pageable) {
        return categoryService.getPageWithEmptyCheck(true , pageable);
    }

    @GetMapping("/nonempty")
    public Page<Category> getPageOfNonEmpty(Pageable pageable) {
        return categoryService.getPageWithEmptyCheck(false , pageable);
    }

    @PostMapping
    @Override
    public Category postObject(@Valid @RequestBody Category object) {
        return categoryService.save(object);
    }

    @PutMapping("/{id}")
    @Override
    public Category putObject(@RequestBody Category object , @PathVariable("id") Integer id) {
        Optional<Category> optional = categoryService.getId(id);

        return optional.map(categoryService::save).orElse(null);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean deleteId(@PathVariable("id") Integer id) {
        return categoryService.deleteId(id);
    }
}

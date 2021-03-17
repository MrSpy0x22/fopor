package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ServiceTpl<Category> {
    public CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> getId(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public boolean deleteId(Integer id) {
        Optional<Category> objectToDelete = categoryRepository.findById(id);

        if (objectToDelete.isPresent()) {
            categoryRepository.delete(objectToDelete.get());
            return true;
        }

        return false;
    }
}

package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /** @deprecated Należy użyć {@link #getPageWithEmptyCheck(boolean, Pageable)} */
    @Override
    public Page<Category> getPage(Pageable pageable) {
        return null;
    }

    public Page<Category> getPageWithEmptyCheck(boolean ignoreEmpty , Pageable pageable) {
        List<Category> categories = categoryRepository.findAll();

        if (!ignoreEmpty) {
            categories = categories
                    .stream()
                    .filter(c -> c.getCategoryPosts().size() > 0)
                    .collect(Collectors.toList());
        }

        return new PageImpl<Category>(categories , pageable , pageable.getPageSize());
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

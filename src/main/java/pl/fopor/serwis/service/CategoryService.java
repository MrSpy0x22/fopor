package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.Utils.FoPorSearchResultModel;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.repository.CategoryRepository;

import java.util.*;

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

    public Map<Integer , String> getList() {
        var tmp = categoryRepository.findAll();
        var ret = new HashMap<Integer , String>();

        tmp.forEach(c -> ret.put(c.getCategoryId() , c.getCategoryName()));

        return ret;
    }

    @Override
    public Page<Category> getPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> getPageNonEmpty(Pageable pageable) {
        return categoryRepository.findNonEmpty(pageable);
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

    public Page<FoPorSearchResultModel> getCategorySearchResult(String text , Pageable pageable) {
        var results = categoryRepository.findAllBySearchCriteria(text , pageable);
        var result = new ArrayList<FoPorSearchResultModel>();

        for (var r : results.getContent()) {
            result.add(new FoPorSearchResultModel(r.getCategoryName() , "Kategoria" , r.getCategoryCreationTime()));
        }

        return new PageImpl<>(result , pageable , result.size());
    }
}

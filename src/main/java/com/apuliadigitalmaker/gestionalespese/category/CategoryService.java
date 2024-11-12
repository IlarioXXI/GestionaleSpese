package com.apuliadigitalmaker.gestionalespese.category;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final String notFoundMessage = "Category not found";

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> findAllCategories() {
        return categoryRepository.findAllByDeletedIsNull();
    }

    public Category findById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
        return category;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
        category.softDelete();
        return categoryRepository.save(category);
    }
    public List<Category> searchCategory(String query) {
        return categoryRepository.findByCategoryNameStartingWithIgnoreCaseAndDeletedIsNull(query);
    }

    public List<?> searchExpenseOrEarningByCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
        if(category.getExpenseEarning()==1){
            return categoryRepository.findEarningsByCategoryType();
        }else {
            return categoryRepository.findExpensesByCategoryType();
        }
    }
}

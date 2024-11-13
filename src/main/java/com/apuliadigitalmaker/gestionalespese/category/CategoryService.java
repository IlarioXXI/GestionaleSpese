package com.apuliadigitalmaker.gestionalespese.category;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import com.apuliadigitalmaker.gestionalespese.earning.EarningRepository;
import com.apuliadigitalmaker.gestionalespese.expense.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        if (!categoryRepository.findById(id).get().getEarnings().isEmpty() || !categoryRepository.findById(id).get().getExpenses().isEmpty()) {
            throw new EntityNotFoundException("Non puoi cancellare una categoria, se hai delle spese o guadagni registrati in essa");
        }
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

    @Transactional
    public Category updateCategory(Integer id, Map<String,Object> update){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            throw new EntityNotFoundException(notFoundMessage);
        }
        Category category = optionalCategory.get();
        update.forEach((key, value) -> {
            switch (key) {
                case "categoryName":
                    category.setCategoryName((String) value);
                    break;
                case "expenseEarning":
                    category.setExpenseEarning((Byte) value);
                    break;
            }
        });
        return categoryRepository.save(category);
    }
}

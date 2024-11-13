package com.apuliadigitalmaker.gestionalespese.category;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import com.apuliadigitalmaker.gestionalespese.earning.EarningRepository;
import com.apuliadigitalmaker.gestionalespese.expense.ExpenseRepository;
import com.apuliadigitalmaker.gestionalespese.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    private static final String notFoundMessage = "Category not found";

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Category> findAllCategories(Integer userId) {
        List<Category> categories = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            if(category.getDeleted()==null && userId.equals(category.getUser().getId())) {
                categories.add(category);
            }
        }
        return categories;
    }

    public Category findById(Integer id,Integer userId) {
        if (categoryRepository.findById(id).get().getDeleted()!=null && userId.equals(categoryRepository.findById(id).get().getUser().getId()) ) {
            throw new EntityNotFoundException(notFoundMessage);
        }
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
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

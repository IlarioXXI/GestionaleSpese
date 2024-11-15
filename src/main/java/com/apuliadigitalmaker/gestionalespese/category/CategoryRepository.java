package com.apuliadigitalmaker.gestionalespese.category;

import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import com.apuliadigitalmaker.gestionalespese.expense.Expense;
import jakarta.validation.constraints.Max;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
     List<Category> findByCategoryNameStartingWithIgnoreCaseAndDeletedIsNull(String query);

     Optional<Category> findCategoryByIdAndDeletedIsNull (Integer id);

     List<Category> findAllByDeletedIsNull();

     @Query("SELECT e FROM Earning e WHERE e.category.expenseEarning = 1")
     List<Earning> findEarningsByCategoryType();

     @Query("SELECT ex FROM Expense ex WHERE ex.category.expenseEarning = 0")
     List<Expense> findExpensesByCategoryType();

}

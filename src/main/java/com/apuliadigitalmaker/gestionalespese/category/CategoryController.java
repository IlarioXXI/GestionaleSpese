package com.apuliadigitalmaker.gestionalespese.category;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.common.JwtUtil;
import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import com.apuliadigitalmaker.gestionalespese.expense.Expense;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories(@RequestHeader("Authorization") String basicAuthString) {
        try {
            List<Category> categories = categoryService.findAllCategories(Integer.valueOf(jwtUtil.extractId(basicAuthString)));

            if (categories.isEmpty()) {
                return ResponseBuilder.notFound("Categories not found");
            } else {
                return ResponseBuilder.success(categories);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id,@RequestHeader("Authorization") String basicAuthString) {
        try{
            Category category = categoryService.findById(id,Integer.valueOf(jwtUtil.extractId(basicAuthString)));
            if (category!=null) {
                return ResponseBuilder.success(category);
            }else{
                throw new EntityNotFoundException("Category with id " + id + " not found");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category,@RequestHeader("Authorization") String basicAuthString){
        try {
            return ResponseBuilder.success(
                    categoryService.findById(category.getId(),
                            Integer.valueOf(jwtUtil.extractId(basicAuthString))));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseBuilder.deleted("Category deleted successfully");
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCategory(@RequestParam String query){
        if (query.length() < 3) {
            return ResponseBuilder.badRequest("Required at least 3 characters");
        }

        List<Category> categories =categoryService.searchCategory(query);
        if (categories.isEmpty()) {
            return ResponseBuilder.notFound("Search has no results");
        }

        return ResponseBuilder.searchResults(categories, categories.size());
    }

    @GetMapping("/search/expenseorearning/{id}")
    public ResponseEntity<?> searchExpenseOrEarningByCategory(@PathVariable Integer id){
        try{
            return  ResponseBuilder.success(categoryService.searchExpenseOrEarningByCategory(id));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id,@RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(categoryService.updateCategory(id,update));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}
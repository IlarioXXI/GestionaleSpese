package com.apuliadigitalmaker.gestionalespese.category;

import com.apuliadigitalmaker.gestionalespese.common.JwtUtil;
import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        try {
            List<Category> categories = categoryService.findAllCategories(Integer.valueOf(strings[1]));

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
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        try{
            Category category = categoryService.findById(id,Integer.valueOf(strings[1]));
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
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        try {
            return ResponseBuilder.success(
                    categoryService.findById(category.getId(),
                            Integer.valueOf(strings[1])));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategoryById(@RequestHeader("Authorization") String basicAuthString) {
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        try{
            categoryService.deleteCategory(Integer.valueOf(strings[1]));
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
    public ResponseEntity<?> searchCategory(@RequestParam String query,@RequestHeader("Authorization") String basicAuthString){
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        if (query.length() < 3) {
            return ResponseBuilder.badRequest("Required at least 3 characters");
        }

        List<Category> categories =categoryService.searchCategory(query,Integer.valueOf(strings[1]));
        if (categories.isEmpty()) {
            return ResponseBuilder.notFound("Search has no results");
        }

        return ResponseBuilder.searchResults(categories, categories.size());
    }

    @GetMapping("/search/expenseorearning/{id}")
    public ResponseEntity<?> searchExpenseOrEarningByCategory(@PathVariable Integer id,@RequestHeader("Authorization") String basicAuthString){
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        try{
            return  ResponseBuilder.success(categoryService.searchExpenseOrEarningByCategory(id,Integer.valueOf(strings[1])));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestHeader("Authorization") String basicAuthString,@RequestBody Map<String, Object> update) {
        String jwtToken = basicAuthString.replace("Bearer ", "");
        String[] strings = jwtUtil.extractUsernameAndId(jwtToken).split("::");
        try {
            return ResponseBuilder.success(categoryService.updateCategory(Integer.valueOf(strings[1]),update));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}
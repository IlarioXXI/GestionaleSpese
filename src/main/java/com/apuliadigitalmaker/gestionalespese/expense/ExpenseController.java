package com.apuliadigitalmaker.gestionalespese.expense;

import com.apuliadigitalmaker.gestionalespese.account.AccountService;
import com.apuliadigitalmaker.gestionalespese.category.CategoryService;
import com.apuliadigitalmaker.gestionalespese.common.JwtUtil;
import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import com.apuliadigitalmaker.gestionalespese.earning.EarningRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses(@RequestHeader("Authorization") String basicAuthString) {
        try {
            List<Expense> expenses = new ArrayList<>();

            for (Expense expense : expenseService.findAllExpenses()){
                if (expense.getDeleted()!=null && expense.getAccount().getUser().getId().equals(Integer.valueOf(jwtUtil.extractId(basicAuthString)))){
                    expenses.add(expense);
                }
            }
            if (expenses.isEmpty()) {
                return ResponseBuilder.notFound("Expense not found");
            } else {
                return ResponseBuilder.success(expenses);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO,@RequestHeader("Authorization") String basicAuthString) {
        try {

            if (accountService.getAccountById(expenseRequestDTO.getAccountId()) == null || categoryService.findById(expenseRequestDTO.getCategoryId(),Integer.valueOf(jwtUtil.extractId(basicAuthString)))== null) {
                return ResponseBuilder.notFound("Account or category not found");
            }else {
                Expense expense = new Expense();
                expense.setCategory(categoryService.findById(expenseRequestDTO.getCategoryId(),Integer.valueOf(jwtUtil.extractId(basicAuthString))));
                expense.setAccount(accountService.getAccountById(expenseRequestDTO.getAccountId()));
                expense.setAmount(expenseRequestDTO.getAmount());
                expense.setExpenseName(expenseRequestDTO.getExpenseName());
                expense.setExpanseDate(expenseRequestDTO.getExpanseDate());

                Expense newExpense = expenseService.saveExpense(expense);
                return ResponseBuilder.success(newExpense);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Integer id,@RequestBody Map<String, Object> update,@RequestHeader("Authorization") String basicAuthString) {
        try {
            return ResponseBuilder.success(expenseService.updateExpense(id,update,Integer.valueOf(jwtUtil.extractId(basicAuthString))));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try{
            return ResponseBuilder.success(expenseService.findById(id));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}

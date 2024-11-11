package com.apuliadigitalmaker.gestionalespese.expense;

import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses() {
        try {
            List<Expense> expenses = expenseService.findAllExpenses();

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
    public ResponseEntity<?> addExpense(@RequestBody Expense expense) {
        try {
            Expense newExpense = expenseService.saveExpense(expense);
            return ResponseBuilder.success(newExpense);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}

package com.apuliadigitalmaker.gestionalespese.expense;

import com.apuliadigitalmaker.gestionalespese.account.AccountRepository;
import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Expense> findAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        for (Expense expense : expenseRepository.findAll()) {
            if (expense.getDeleted() == null){
                expenses.add(expense);
            }
        }
        return expenses;
    }

    public Optional<Expense> findById(Integer id) {
        if (expenseRepository.findById(id).get().getDeleted() != null) {
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
        return expenseRepository.findById(id);
    }

    public Expense saveExpense(Expense expense){
        Long amount = expense.getAmount().longValue();
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }else{
            expense.getAccount().getActualBalance().subtract(expense.getAmount());
            accountRepository.save(expense.getAccount());
            return expenseRepository.save(expense);
        }
    }

    @Transactional
    public Expense updateExpense(Integer id, Map<String, Object> update,Integer userId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (!expenseRepository.findById(id).get().getAccount().getUser().getId().equals(userId)){
            throw new EntityNotFoundException("User not found");
        }
        if (optionalExpense.isPresent()){
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
        Expense expense = optionalExpense.get();
        update.forEach((key, value) -> {
            switch (key) {
                case "amount":
                    expense.setAmount((BigDecimal) value);
                    break;
                case "expenseName":
                    expense.setExpenseName((String) value);
                    break;
                case "expanseDate":
                    expense.setExpanseDate((Instant) value);
                    break;
            }
        });
        return expenseRepository.save(expense);
    }
}

package com.apuliadigitalmaker.gestionalespese.expense;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository <Expense, Integer> {

}

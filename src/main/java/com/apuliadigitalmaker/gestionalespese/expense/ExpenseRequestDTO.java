package com.apuliadigitalmaker.gestionalespese.expense;

import java.math.BigDecimal;
import java.time.Instant;

public class ExpenseRequestDTO {
    private Integer accountId;
    private Integer categoryId;
    private String expenseName;
    private BigDecimal amount;
    private Instant expanseDate;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getExpanseDate() {
        return expanseDate;
    }

    public void setExpanseDate(Instant expanseDate) {
        this.expanseDate = expanseDate;
    }
}

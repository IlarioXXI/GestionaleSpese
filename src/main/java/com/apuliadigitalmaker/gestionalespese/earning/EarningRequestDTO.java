package com.apuliadigitalmaker.gestionalespese.earning;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class EarningRequestDTO {
    private Integer accountId;
    private Integer categoryId;
    private String earningName;
    private BigDecimal amount;
    private Instant earningDate;

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

    public String getEarningName() {
        return earningName;
    }

    public void setEarningName(String earningName) {
        this.earningName = earningName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getEarningDate() {
        return earningDate;
    }

    public void setEarningDate(Instant earningDate) {
        this.earningDate = earningDate;
    }
}

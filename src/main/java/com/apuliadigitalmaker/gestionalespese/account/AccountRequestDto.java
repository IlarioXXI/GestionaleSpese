package com.apuliadigitalmaker.gestionalespese.account;

import java.math.BigDecimal;

public class AccountRequestDto {

    private String accountName;
    private BigDecimal initialBalance;
    private Integer userId;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}

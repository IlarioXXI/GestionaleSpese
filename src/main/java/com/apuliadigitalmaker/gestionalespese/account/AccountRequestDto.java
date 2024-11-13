package com.apuliadigitalmaker.gestionalespese.account;

import java.math.BigDecimal;

public class AccountRequestDto {

    private String accountName;
    private BigDecimal initialBalance;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}

package com.apuliadigitalmaker.gestionalespese.account;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    private static final String notFoundMessage = "Account not found";

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    };

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Integer id){
        return accountRepository.getAccountsById(id);
    }

    @Transactional
    public Account updateAccount(Integer id, Map<String, Object> update){
        Optional<Account> optionalAccount = accountRepository.findAccountById(id);
        if (optionalAccount.isEmpty()){
            throw new EntityNotFoundException(notFoundMessage);
        }
        Account account =optionalAccount.get();
        update.forEach((key,value) -> {
            switch (key){
                case "accountName" :
                    account.setAccountName((String) value);
                    break;
                case "actualBalance" :
                    account.setActualBalance((BigDecimal) value);
                    break;
            }
        });
        return accountRepository.save(account);
    }

    @Transactional
    public Account deleteAccount(Integer id) {
        Account account = accountRepository.findAccountById(id)
                .orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
        account.softDelete();
        return accountRepository.save(account);
    }

    public List<Account> searchAccount(String query) {
        return accountRepository.findByAccountNameStartingWithIgnoreCaseAndDeletedIsNull(query);
    }

}

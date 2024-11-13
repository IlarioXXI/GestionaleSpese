package com.apuliadigitalmaker.gestionalespese.account;

import com.apuliadigitalmaker.gestionalespese.category.Category;
import com.apuliadigitalmaker.gestionalespese.category.CategoryRepository;
import com.apuliadigitalmaker.gestionalespese.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    private static final String notFoundMessage = "Account not found";

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    };

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Integer id){
        return accountRepository.getAccountsById(id);
    }

    public List<Account> getAllAccountsById(Integer id){
        List<Account> accounts = new ArrayList<>();
        for (Account account : findAllAccounts()) {
            if (account.getId().equals(id) && account.getDeleted()==null) {
                accounts.add(account);
            }
        }
        return accounts;
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
        for (Category category :  account.getUser().getCategories()) {
            category.softDelete();
            categoryRepository.save(category);
        }
        return accountRepository.save(account);
    }

    public List<Account> searchAccount(String query,Integer userId) {
        List<Account> accounts = new ArrayList<>();
        for (Account account : accountRepository.findByAccountNameStartingWithIgnoreCaseAndDeletedIsNull(query)) {
            if(account.getUser().getId().equals(userId)){
                accounts.add(account);
            }
        }
        return accounts;
    }



}

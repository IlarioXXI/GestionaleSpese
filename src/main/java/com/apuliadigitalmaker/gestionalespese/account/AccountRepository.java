package com.apuliadigitalmaker.gestionalespese.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getAccountsById(Integer id);
    Optional<Account> findAccountById(Integer id);
    List<Account> findByAccountNameStartingWithIgnoreCaseAndDeletedIsNull(String query);
    List<Account> findAccountsByDeletedIsNullAndUserNotNullAndUser_Id(Integer userId);
}

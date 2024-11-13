package com.apuliadigitalmaker.gestionalespese.account;

import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import com.apuliadigitalmaker.gestionalespese.user.UserRepository;
import com.apuliadigitalmaker.gestionalespese.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccounts() {
        try{
            List<Account> accounts = accountService.findAllAccounts();

            if (accounts.isEmpty()) {
                return ResponseBuilder.notFound("No account is present, please create one first.");
            }else {
                return ResponseBuilder.success(accounts);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllAccountsById(@PathVariable Integer id) {
        try{
            List<Account> accounts = new ArrayList<>();
            accounts.addAll(accountRepository.findAccountsByDeletedIsNull());
            if (accounts.isEmpty()) {
                return ResponseBuilder.notFound("No account is present, please create one first.");
            }else {
                return ResponseBuilder.success(accounts);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@RequestBody AccountRequestDto accountRequestDto) {
        try {
            Account account = new Account();
            if (userService.findUserById(accountRequestDto.getUserId()) != null) {
                account.setUser(userService.findUserById(accountRequestDto.getUserId()));
            } else throw new EntityNotFoundException("User not found");

            account.setAccountName(accountRequestDto.getAccountName());
            account.setInitialBalance(accountRequestDto.getInitialBalance());
            account.setActualBalance(accountRequestDto.getInitialBalance());
            return ResponseBuilder.success(accountService.addAccount(account));
        }catch (EntityNotFoundException e) {
            return ResponseBuilder.error();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id,@RequestBody AccountRequestDto accountRequestDto) {
        try{
            accountRepository.findAccountsByDeletedIsNullAndUserNotNullAndUser_Id(accountRequestDto.getUserId());
            if (accountService.getAccountById(id)==null){
                return ResponseBuilder.notFound("Account non found");
            }
            return ResponseBuilder.success(accountService.getAccountById(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Integer id,@RequestBody Map<String, Object> update){
        try{
            return ResponseBuilder.success(accountService.updateAccount(id,update));
        }
        catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id){
        try{
            accountService.deleteAccount(id);
            return ResponseBuilder.deleted("Account deleted successfully");
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccount(@RequestParam String query){
        if (query.length() < 3) {
            return ResponseBuilder.badRequest("Required at least 3 characters");
        }

        List<Account> accounts =accountService.searchAccount(query);
        if (accounts.isEmpty()) {
            return ResponseBuilder.notFound("Search has no results");
        }

        return ResponseBuilder.searchResults(accounts, accounts.size());
    }
}

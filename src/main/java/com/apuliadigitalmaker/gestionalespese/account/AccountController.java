package com.apuliadigitalmaker.gestionalespese.account;

import com.apuliadigitalmaker.gestionalespese.common.JwtUtil;
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
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/allwithoutauth")
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccountsById(@RequestHeader("Authorization") String basicAuthString) {
        try{
            List<Account> accounts = new ArrayList<>();
            accounts.addAll(accountService.getAllAccountsById(Integer.valueOf(jwtUtil.extractId(basicAuthString))));
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
    public ResponseEntity<?> addAccount(@RequestBody AccountRequestDto accountRequestDto,@RequestHeader("Authorization") String basicAuthString) {
        try {
            Account account = new Account();
            if (userService.findUserById(Integer.valueOf(jwtUtil.extractId(basicAuthString))) != null) {
                account.setUser(userService.findUserById(Integer.valueOf(jwtUtil.extractId(basicAuthString))));
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
    public ResponseEntity<?> getAccountById(@PathVariable Integer id,@RequestHeader("Authorization") String basicAuthString) {
        try{
            accountRepository.findAccountsByDeletedIsNullAndUserNotNullAndUser_Id(Integer.valueOf(jwtUtil.extractId(basicAuthString)));
            if (accountService.getAccountById(id)==null){
                return ResponseBuilder.notFound("Account non found");
            }
            return ResponseBuilder.success(accountService.getAccountById(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestHeader("Authorization") String basicAuthString,@RequestBody Map<String, Object> update){
        try{
            return ResponseBuilder.success(accountService.updateAccount(Integer.valueOf(jwtUtil.extractId(basicAuthString)),update));
        }
        catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestHeader("Authorization") String basicAuthString){
        try{
            accountService.deleteAccount(Integer.valueOf(jwtUtil.extractId(basicAuthString)));
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
    public ResponseEntity<?> searchAccount(@RequestParam String query,@RequestHeader("Authorization") String basicAuthString){
        if (query.length() < 3) {
            return ResponseBuilder.badRequest("Required at least 3 characters");
        }

        List<Account> accounts =accountService.searchAccount(query,Integer.valueOf(jwtUtil.extractId(basicAuthString)));
        if (accounts.isEmpty()) {
            return ResponseBuilder.notFound("Search has no results");
        }

        return ResponseBuilder.searchResults(accounts, accounts.size());
    }
}

package com.apuliadigitalmaker.gestionalespese.account;

import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccounts() {
        try{
            List<Account> accounts = accountService.findAllAccounts();

            if (accounts.isEmpty()) {
                return ResponseBuilder.notFound("Account not found");
            }else {
                return ResponseBuilder.success(accounts);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        try{
            return ResponseBuilder.success(accountService.addAccount(account));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id){
        try{
            if (accountService.getAccountById(id)==null){
                return ResponseBuilder.notFound("Account non found");
            }
            return ResponseBuilder.success(accountService.getAccountById(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/{id}")
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

    @DeleteMapping("/{id}")
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
}

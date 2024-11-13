package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.account.AccountService;
import com.apuliadigitalmaker.gestionalespese.category.CategoryService;
import com.apuliadigitalmaker.gestionalespese.common.JwtUtil;
import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import com.apuliadigitalmaker.gestionalespese.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/earning")
public class EarningController {
    @Autowired
    private EarningService earningService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEarning() {
        try {
            List<Earning> earnings = earningService.findAllEarnings();

            if (earnings.isEmpty()) {
                return ResponseBuilder.notFound("Earning not found");
            } else {
                return ResponseBuilder.success(earnings);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addEarning(@RequestBody EarningRequestDTO earningRequestDTO,@RequestHeader("Authorization") String basicAuthString) {
        try {

            if (accountService.getAccountById(earningRequestDTO.getAccountId()) == null || categoryService.findById(earningRequestDTO.getCategoryId(),Integer.valueOf(jwtUtil.extractId(basicAuthString)))== null) {
                return ResponseBuilder.notFound("Account or category not found");
            }else {
                Earning earning = new Earning();
                earning.setCategory(categoryService.findById(earningRequestDTO.getCategoryId(),Integer.valueOf(jwtUtil.extractId(basicAuthString))));
                earning.setAccount(accountService.getAccountById(earningRequestDTO.getAccountId()));
                earning.setAmount(earningRequestDTO.getAmount());
                earning.setEarningName(earningRequestDTO.getEarningName());
                earning.setEarningDate(earningRequestDTO.getEarningDate());

                Earning newEarning = earningService.saveEarning(earning);
                return ResponseBuilder.success(newEarning);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateEarning(@PathVariable Integer id,@RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(earningService.updateEarning(id,update));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try{
            return ResponseBuilder.success(earningService.findById(id));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}

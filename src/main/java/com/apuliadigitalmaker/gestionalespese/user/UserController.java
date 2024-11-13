package com.apuliadigitalmaker.gestionalespese.user;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.common.JwtUtil;
import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        System.out.println("Metodo getAllUsers invocato");
        try {
            List<User> users = userService.findAll();

            if (users.isEmpty()) {
                return ResponseBuilder.notFound("Users not found");
            } else {
                return ResponseBuilder.success(users);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User newUser = userService.saveUser(user);
            return ResponseBuilder.success(newUser);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String basicAuthString,@RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(userService.updateUser(Integer.valueOf(jwtUtil.extractId(basicAuthString)),update));
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String basicAuthString) {
        try{
            userService.deleteUser(Integer.valueOf(jwtUtil.extractId(basicAuthString)));
            return ResponseBuilder.deleted("User deleted successfully");
        }catch (EntityNotFoundException e){
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}

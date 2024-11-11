package com.apuliadigitalmaker.gestionalespese.user;

import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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

}

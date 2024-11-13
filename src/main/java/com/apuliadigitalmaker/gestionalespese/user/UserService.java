package com.apuliadigitalmaker.gestionalespese.user;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.account.AccountRepository;
import com.apuliadigitalmaker.gestionalespese.category.Category;
import com.apuliadigitalmaker.gestionalespese.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private static final String userRegistered = "User registered";
    private static final String notFoundMessage = "Account not found";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<User> findByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    public User saveUser(User user) throws BadRequestException {
        for(User u : userRepository.findAll()) {
            if(u.getUsername().equals(user.getUsername())) {
                throw new BadRequestException(userRegistered);
            }
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Integer id, Map<String, Object> update){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new EntityNotFoundException(notFoundMessage);
        }
        User user = optionalUser.get();
        update.forEach((key,value) -> {
            switch (key){
                case "username" :
                    user.setUsername((String) value);
                    break;
                case "password" :
                    user.setUsername((String) value);
                    break;
                case "email" :
                    user.setEmail((String) value);
                    break;
            }
        });
        return userRepository.save(user);
    }


    @Transactional
    public User deleteUser(Integer id){
        User user = userRepository.findUserById(id)
                .orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
        user.softDelete();
        for (Category category : user.getCategories()) {
            category.setDeleted(Instant.now());
            categoryRepository.save(category);
        }
        for (Account account : user.getAccount()) {
            account.setDeleted(Instant.now());
            accountRepository.save(account);
        }
        return userRepository.save(user);
    }

    public User findUserById(Integer id){
        return userRepository.findUserById(id)
                .orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
    }

    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if(user.getDeleted()==null){
                users.add(user);
            }
        }
        return users;
    }
}

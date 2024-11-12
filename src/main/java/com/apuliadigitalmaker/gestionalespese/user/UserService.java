package com.apuliadigitalmaker.gestionalespese.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private static final String userRegistered = "User registered";
    private static final String notFoundMessage = "Account not found";


    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

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
        return userRepository.save(user);
    }

    public User findUserById(Integer id){
        return userRepository.findUserById(id)
                .orElseThrow(()-> new EntityNotFoundException(notFoundMessage));
    }
}

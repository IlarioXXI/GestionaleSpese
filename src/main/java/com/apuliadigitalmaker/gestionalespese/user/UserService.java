package com.apuliadigitalmaker.gestionalespese.user;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String userRegistered = "User registered";

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
}

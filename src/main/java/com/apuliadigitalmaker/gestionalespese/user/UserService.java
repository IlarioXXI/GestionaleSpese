package com.apuliadigitalmaker.gestionalespese.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String notFoundMessage = "User not found";

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    public User saveUser(UserRequestDTO userRequestDTO) {

        User newUser = new User();
        newUser.setUsername(userRequestDTO.getUsername());
        newUser.setPassword(userRequestDTO.getPassword());
        return userRepository.save(newUser);
    }
}

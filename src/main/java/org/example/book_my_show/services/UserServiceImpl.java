package org.example.book_my_show.services;


import org.example.book_my_show.exceptions.UserAlreadyExitsException;
import org.example.book_my_show.models.User;
import org.example.book_my_show.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signUpUser(String name, int age, String email, String phoneNumber, String password) throws UserAlreadyExitsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) throw new UserAlreadyExitsException("User "+email+" already exists!");

        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }
}

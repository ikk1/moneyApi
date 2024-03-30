package com.junior.money.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.junior.money.api.models.User;
import com.junior.money.api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public UserDetails findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}

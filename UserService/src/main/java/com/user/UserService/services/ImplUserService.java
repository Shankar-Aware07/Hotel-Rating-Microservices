package com.user.UserService.services;

import com.user.UserService.entity.User;
import com.user.UserService.exceptions.ResourceNotFoundException;
import com.user.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImplUserService implements InUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server !!"));
    }
}

package com.user.UserService.services;

import com.user.UserService.entity.User;

import java.util.List;

public interface InUserService {

    //Create
    User save(User user);

    //Get all
    List<User> getAllUser();

    //Get by UserId
    User getUser(String userId);
}

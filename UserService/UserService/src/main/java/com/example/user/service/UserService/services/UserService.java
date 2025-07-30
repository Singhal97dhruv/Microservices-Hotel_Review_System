package com.example.user.service.UserService.services;

import com.example.user.service.UserService.entities.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> getAllUsers();

    User getUser(String userId);

}

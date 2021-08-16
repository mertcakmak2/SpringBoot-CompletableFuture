package com.example.future.service;

import com.example.future.filter.FilterRequest;
import com.example.future.model.User;

import java.util.List;

public interface UserService {

    String saveUser(User user);
    List<User> filterUsers(List<FilterRequest> filterRequests, String filterType);
}

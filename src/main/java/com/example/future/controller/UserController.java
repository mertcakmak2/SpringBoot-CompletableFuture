package com.example.future.controller;

import com.example.future.model.User;
import com.example.future.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    private String saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

}

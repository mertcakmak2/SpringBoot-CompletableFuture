package com.example.future.service;

import com.example.future.model.User;
import com.example.future.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    public String saveUser(User user) {

        logger.info("service saveUser method begin.");

        CompletableFuture<Void> saveUserFuture = CompletableFuture.runAsync(() -> {
            userRepository.save(user);
        });

        logger.info("service saveUser method end.");

        return "saved user";
    }
}

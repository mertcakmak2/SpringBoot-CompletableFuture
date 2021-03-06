package com.example.future.service;

import com.example.future.filter.FilterRequest;
import com.example.future.model.User;
import com.example.future.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    public String saveUser(User user) {

        logger.info("service saveUser method begin.");

        //userRepository.save(user);

        CompletableFuture<Void> saveUserFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000L);
                User savedUser = userRepository.save(user);
                logger.info(savedUser.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // thenRun => callback function
        }).thenRun(() -> {
            System.out.println("then run");
        });

        logger.info("service saveUser method end.");

        return "saved user";
    }


}

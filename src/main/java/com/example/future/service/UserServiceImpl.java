package com.example.future.service;

import com.example.future.filter.FilterRequest;
import com.example.future.model.User;
import com.example.future.model.UserDto;
import com.example.future.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<User> filterUsers(List<FilterRequest> filterRequests, String filterType) {

        List<String> queryArray = new ArrayList<>();

        for (FilterRequest filterRequest : filterRequests) {

            switch (filterRequest.getOperation()) {
                case EQ:
                    String[] values = filterRequest.getValues();
                    String value = values[0];
                    queryArray.add(filterRequest.getFieldName() + " = " + "'" + value + "'");
                    break;
                case NOTEQ:
                    String[] notEqValues = filterRequest.getValues();
                    String notEqValue = notEqValues[0];
                    queryArray.add(filterRequest.getFieldName() + " != " + "'" + notEqValue + "'");
                    break;
                case IN:
                    String[] inValues = filterRequest.getValues();
                    for (int i = 0; i < inValues.length; i++) {
                        inValues[i] = String.format("'%s'", inValues[i]);
                    }
                    String strValues = String.join(",", inValues);
                    queryArray.add(filterRequest.getFieldName() + " in (" + strValues + ") ");
                    break;
                case NOTIN:
                    String[] notInValues = filterRequest.getValues();
                    for (int i = 0; i < notInValues.length; i++) {
                        notInValues[i] = String.format("'%s'", notInValues[i]);
                    }
                    String notInValue = String.join(",", notInValues);
                    queryArray.add(filterRequest.getFieldName() + " not in (" + notInValue + ") ");
                    break;
                case LESS:
                    String[] lessValue = filterRequest.getValues();
                    queryArray.add(filterRequest.getFieldName() + " < " + String.format("'%s'", lessValue[0]));
                    break;
                case GREATHER:
                    String[] greatherValue = filterRequest.getValues();
                    queryArray.add(filterRequest.getFieldName() + " > " + String.format("'%s'", greatherValue[0]));
                    break;
                case LIKE:
                    String[] likeValues = filterRequest.getValues();
                    String likeStr ="'%"+ likeValues[0] +"%'";
                    queryArray.add(filterRequest.getFieldName() + " LIKE " + likeStr);
                    break;
                default:
                    break;
            }

        }
        String type = String.format(" %s ", filterType);
        String query = String.join(type, queryArray);
        query = "select u from User u where " + query;
        System.out.println(query);

        List<User> users = entityManager.createQuery(query).getResultList();
        return users;
    }
}

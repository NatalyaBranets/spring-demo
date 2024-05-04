package com.example.springdemo.service;

import com.example.springdemo.core.user.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    /**
     * Add new user
     */
    void createUser(User user);

    /**
     * @param from - start date
     * @param to - end date
     * @return list of users according to DateBirth range
     */
    List<User> findByBirthDateRange(Date from, Date to);

    /**
     * Remove user by id from db
     */
    void delete(String userId);

    /**
     * Update user's fields in db
     */
    void update(String userId, User userToUpdate);

    /**
     * Update user's email in db
     */
    void updateEmail(String userId, String email);
}

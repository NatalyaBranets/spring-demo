package com.example.springdemo.repository;

import com.example.springdemo.core.user.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    /**
     * Memory allocation for user saving. Key = id; value = user.
     */
    private Map<String, User> usersDatabase = new HashMap<>();

    public void save(User user) {
        String id = String.valueOf(this.usersDatabase.size() + 1);
        this.usersDatabase.put(id, user);
    }

    public List<User> findByBirthDateRange(Date from, Date to) {
        return usersDatabase.values()
                .stream()
                .filter(user -> user.getBirthDate().after(from) && user.getBirthDate().before(to) )
                .collect(Collectors.toList());
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(this.usersDatabase.get(id));
    }

    public void delete(String id) {
        this.usersDatabase.remove(id);
    }

    public void updateEmailById(String userId, String email) {
        User user = this.usersDatabase.get(userId);
        user.setEmail(email);

        this.usersDatabase.put(userId, user);
    }

    public void updateById(String id, User user) {
        this.usersDatabase.put(id, user);
    }
}

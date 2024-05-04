package com.example.springdemo.service;

import com.example.springdemo.core.user.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.util.UserUtil;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.springdemo.core.ConstantValidator.EMAIL_PATTERN;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Value("${validation.user.age}")
    private int age;

    @Override
    public void createUser(User user) {
        boolean isValidAge = UserUtil.validateUserAge(user.getBirthDate(), this.age);
        if (isValidAge) {
            this.userRepository.save(user);
        } else {
            throw new ValidationException("Date of birth user should be 18 years and more.");
        }
    }

    @Override
    public List<User> findByBirthDateRange(Date from, Date to) {
        return this.userRepository.findByBirthDateRange(from, to);
    }

    @Override
    public void delete(String id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            this.userRepository.delete(id);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public void update(String userId, User userToUpdate) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // update user's fields
            User user = userOptional.get();

            boolean isValidAge = UserUtil.validateUserAge(user.getBirthDate(), this.age);
            if (isValidAge) {
                user.setBirthDate(userToUpdate.getBirthDate());
            } else {
                throw new ValidationException("Date of birth user should be 18 years and more.");
            }

            user.setAddress(userToUpdate.getAddress());
            user.setLastName(userToUpdate.getLastName());
            user.setFirstName(userToUpdate.getFirstName());
            user.setPhoneNumber(userToUpdate.getPhoneNumber());
            user.setEmail(userToUpdate.getEmail());

            // save user into db
            this.userRepository.updateById(userId, user);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public void updateEmail(String userId, String email) {
        if (email != null && !email.trim().isEmpty() && email.matches(EMAIL_PATTERN)) {
            this.userRepository.updateEmailById(userId, email);
        } else {
            throw new ValidationException("Email is invalid.");
        }
    }
}

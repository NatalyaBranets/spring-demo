package com.example.springdemo.controller;

import com.example.springdemo.core.user.User;
import com.example.springdemo.core.user.UserRequestParamsDTO;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        this.userService.createUser(user);
        return ResponseEntity.ok("User is created");
    }

    @PatchMapping( "/updateEmail/{id}")
    public ResponseEntity<String> updateEmail(@RequestBody UserRequestParamsDTO requestParamsDTO,
                                              @PathVariable("id") String userId) {
        this.userService.updateEmail(userId, requestParamsDTO.getEmail());
        return ResponseEntity.ok("Email of user is updated");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody User userToUpdate,
                                                 @PathVariable(value = "id") String userId) {
        this.userService.update(userId, userToUpdate);
        return ResponseEntity.ok("User is updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id){
        this.userService.delete(id);
        return ResponseEntity.ok("User is deleted");
    }

    @GetMapping("/findByBirthDateRange")
    public ResponseEntity<List<User>> getByBirthDateRange(@RequestParam long from,
                                                          @RequestParam long to) {
        List<User> users = this.userService.findByBirthDateRange(new Date(from), new Date(to));
        return ResponseEntity.ok().body(users);
    }
}

package com.nthomas.springbootwithcucumber.rest;

import com.nthomas.springbootwithcucumber.entity.User;
import com.nthomas.springbootwithcucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public User getUser(@PathVariable("email") String email) {
        return userService.getUser(email);
    }

    @PutMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping({"", "/{email}"})
    public User updateUser(@PathVariable("email") Optional<String> email, @RequestBody User user) {
        return userService.updateUser(email.isPresent() ? email.get() : user.getEmail(), user);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user) {
        userService.deleteUserByEmail(user.getEmail());
    }
}

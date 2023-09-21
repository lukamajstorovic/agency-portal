package com.lmajstorovic.agencyportal.controller;

import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(path = "/register")
    public void registerUser(@RequestBody User user) {
        userService.addNewUser(user);
    }

    @GetMapping(path = "/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping(path = "/rank/{rank}")
    public List<User> getUsersByRank(@PathVariable("rank") String rank) {
        return userService.getUsersByRank(rank);
    }

    @PutMapping(path = "/update")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }
}
